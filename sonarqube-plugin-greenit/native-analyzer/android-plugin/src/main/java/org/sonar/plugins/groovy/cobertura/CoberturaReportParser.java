/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
 *  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.groovy.cobertura;

import static java.util.Locale.ENGLISH;
import static org.sonar.api.utils.ParsingUtils.parseNumber;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.staxmate.in.SMInputCursor;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.coverage.NewCoverage;
import org.sonar.api.utils.MessageException;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.utils.StaxParser;

public class CoberturaReportParser {

  private static final Logger LOG = Loggers.get(CoberturaReportParser.class);

  private final SensorContext context;
  private final FileSystem fileSystem;

  public CoberturaReportParser(SensorContext context, final FileSystem fileSystem) {
    this.context = context;
    this.fileSystem = fileSystem;
  }

  /** Parse a Cobertura xml report and create measures accordingly */
  public void parseReport(File xmlFile) {
    try {
      parsePackages(xmlFile);
    } catch (XMLStreamException e) {
      throw MessageException.of("Unable to parse Cobertura report.", e);
    }
  }

  private void parsePackages(File xmlFile) throws XMLStreamException {
    StaxParser fileParser =
        new StaxParser(
            rootCursor -> {
              rootCursor.advance();
              collectPackageMeasures(rootCursor.descendantElementCursor("package"));
            });
    fileParser.parse(xmlFile);
  }

  private void collectPackageMeasures(SMInputCursor pack) throws XMLStreamException {
    while (pack.getNext() != null) {
      Map<String, ParsingResult> resultByFilename = new HashMap<>();
      collectFileMeasures(pack.descendantElementCursor("class"), resultByFilename);
      handleFileMeasures(resultByFilename);
    }
  }

  private static void handleFileMeasures(Map<String, ParsingResult> resultByFilename) {
    for (ParsingResult parsingResult : resultByFilename.values()) {
      if (parsingResult.inputFile != null
          && Groovy.KEY.equals(parsingResult.inputFile.language())) {
        parsingResult.coverage.save();
      } else {
        LOG.warn("File not found: {}", parsingResult.filename);
      }
    }
  }

  @CheckForNull
  private InputFile getInputFile(String filename) {
    try {
      InputFile file =
          fileSystem.inputFile(fileSystem.predicates().matchesPathPattern("**/" + filename));
      if (file != null) {
        return file;
      }
    } catch (IllegalArgumentException e) {
      LOG.warn("Multiple matches for coverage of '{}' found", filename);
    }
    return null;
  }

  private void collectFileMeasures(SMInputCursor clazz, Map<String, ParsingResult> resultByFilename)
      throws XMLStreamException {
    while (clazz.getNext() != null) {
      String fileName = clazz.getAttrValue("filename");
      ParsingResult parsingResult =
          resultByFilename.computeIfAbsent(
              fileName,
              newFileNme -> {
                InputFile inputFile = getInputFile(fileName);
                NewCoverage onFile = context.newCoverage().onFile(inputFile);
                return new ParsingResult(fileName, inputFile, onFile);
              });
      collectFileData(clazz, parsingResult);
    }
  }

  private static void collectFileData(SMInputCursor clazz, ParsingResult parsingResult)
      throws XMLStreamException {
    SMInputCursor line = clazz.childElementCursor("lines").advance().childElementCursor("line");
    while (line.getNext() != null) {
      int lineId = Integer.parseInt(line.getAttrValue("number"));
      boolean validLine = parsingResult.isValidLine(lineId);
      if (!validLine && parsingResult.fileExists()) {
        LOG.info(
            "Hit on invalid line for file "
                + parsingResult.filename
                + " (line: "
                + lineId
                + "/"
                + parsingResult.inputFile.lines()
                + ")");
      }
      try {
        int hits = (int) parseNumber(line.getAttrValue("hits"), ENGLISH);
        if (validLine) {
          parsingResult.coverage = parsingResult.coverage.lineHits(lineId, hits);
        }
      } catch (ParseException e) {
        throw MessageException.of("Unable to parse Cobertura report.", e);
      }

      String isBranch = line.getAttrValue("branch");
      String text = line.getAttrValue("condition-coverage");
      if (validLine && StringUtils.equals(isBranch, "true") && StringUtils.isNotBlank(text)) {
        String[] conditions = StringUtils.split(StringUtils.substringBetween(text, "(", ")"), "/");
        parsingResult.coverage =
            parsingResult.coverage.conditions(
                lineId, Integer.parseInt(conditions[1]), Integer.parseInt(conditions[0]));
      }
    }
  }

  private static class ParsingResult {
    private final String filename;
    @Nullable private final InputFile inputFile;
    private NewCoverage coverage;

    public ParsingResult(String filename, @Nullable InputFile inputFile, NewCoverage coverage) {
      this.filename = filename;
      this.inputFile = inputFile;
      this.coverage = coverage;
    }

    public boolean isValidLine(int lineId) {
      return fileExists() && lineId > 0 && lineId <= inputFile.lines();
    }

    public boolean fileExists() {
      return inputFile != null;
    }
  }
}
