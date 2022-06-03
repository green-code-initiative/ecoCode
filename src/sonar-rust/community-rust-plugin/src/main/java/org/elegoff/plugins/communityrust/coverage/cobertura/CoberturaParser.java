/**
 * Community Rust Plugin
 * Copyright (C) 2021 Eric Le Goff
 * mailto:community-rust AT pm DOT me
 * http://github.com/elegoff/sonar-rust
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
package org.elegoff.plugins.communityrust.coverage.cobertura;

import com.ctc.wstx.exc.WstxEOFException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.staxmate.in.SMInputCursor;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.coverage.NewCoverage;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class CoberturaParser {

  private static final Logger LOG = Loggers.get(CoberturaParser.class);
  private static final String SOURCES = "sources";
  private static final String SOURCE = "source";
  private static final String PACKAGES = "packages";
  private static final String LINES = "lines";
  private static final String LINE = "line";
  private static final String NUMBER = "number";
  private static final String FILENAME = "filename";
  private static final String HITS = "hits";
  private static final String BRANCH = "branch";
  private static final String CONDITION_COVERAGE = "condition-coverage";

  private int unresolvedNb;

  private static List<File> lookupBaseDirs(SMInputCursor sources, File baseDir) throws XMLStreamException {
    List<File> baseDirs = new ArrayList<>();
    SMInputCursor source = sources.childElementCursor(SOURCE);
    if (source.getNext() != null) {
      do {
        String path = FilenameUtils.normalize(source.collectDescendantText());
        if (!StringUtils.isBlank(path)) {
          var baseDirectory = new File(path);
          if (baseDirectory.isDirectory()) {
            baseDirs.add(baseDirectory);
          } else {
            LOG.warn("Invalid directory path {} found in 'source' element", path);
          }
        }
      } while (source.getNext() != null);
    }
    if (baseDirs.isEmpty()) {
      return Collections.singletonList(baseDir);
    }
    return baseDirs;
  }

  private static void readFileContent(SMInputCursor classCursor, NewCoverage coverage) throws XMLStreamException {
    SMInputCursor line = classCursor.childElementCursor(LINES).advance().childElementCursor(LINE);
    while (line.getNext() != null) {
      var linum = Integer.parseInt(line.getAttrValue(NUMBER));
      coverage.lineHits(linum, Integer.parseInt(line.getAttrValue(HITS)));

      String isBranch = line.getAttrValue(BRANCH);
      String text = line.getAttrValue(CONDITION_COVERAGE);
      if (!StringUtils.equals(isBranch, "true") || !StringUtils.isNotBlank(text)) {
        continue;
      }
      String[] conditions = StringUtils.split(StringUtils.substringBetween(text, "(", ")"), "/");
      coverage.conditions(linum, Integer.parseInt(conditions[1]), Integer.parseInt(conditions[0]));
    }
  }

  public void importReport(File xmlFile, SensorContext context, final Map<InputFile, NewCoverage> coverageMap) throws XMLStreamException {
    LOG.info("Importing report '{}'", xmlFile);
    unresolvedNb = 0;

    StaxParser parser;
    parser = new StaxParser(rootCursor -> {
      File baseDir = context.fileSystem().baseDir();
      List<File> baseDirs = Collections.singletonList(baseDir);
      try {
        rootCursor.advance();
      } catch (WstxEOFException e) {
        LOG.debug("Reaching end of file unexpectedly", e);
        throw new CoberturaException();
      }
      SMInputCursor cursor = rootCursor.childElementCursor();
      while (cursor.getNext() != null) {
        if (SOURCES.equals(cursor.getLocalName())) {
          baseDirs = lookupBaseDirs(cursor, baseDir);
        } else if (PACKAGES.equals(cursor.getLocalName())) {
          getFileMeasures(cursor.descendantElementCursor("class"), context, coverageMap, baseDirs);
        }
      }
    });
    parser.parse(xmlFile);
    if (unresolvedNb > 1) {
      LOG.error("{} file paths can not be resolved, coverage measures will be ignored for those files", unresolvedNb);
    }
  }

  private void getFileMeasures(SMInputCursor classCursor, SensorContext context, Map<InputFile, NewCoverage> coverageData, List<File> baseDirectories)
    throws XMLStreamException {
    while (classCursor.getNext() != null) {
      String filename = FilenameUtils.normalize(classCursor.getAttrValue(FILENAME));
      var inputFile = resolve(context, baseDirectories, filename);
      if (inputFile != null) {
        NewCoverage coverage = coverageData.computeIfAbsent(inputFile, f -> context.newCoverage().onFile(f));
        readFileContent(classCursor, coverage);
      } else {
        classCursor.advance();
      }
    }
  }

  @Nullable
  private InputFile resolve(SensorContext context, List<File> baseDirectories, String filename) {
    String absolutePath;
    var file = new File(filename);
    if (file.isAbsolute()) {
      if (!file.exists()) {
        logUnresolvedFile("The file name '{}' of the coverage report can not be resolved, the file does not exist in all <source>.", filename);
      }
      absolutePath = file.getAbsolutePath();
    } else {
      List<File> fileList = baseDirectories.stream()
        .map(base -> new File(base, filename))
        .filter(File::exists)
        .collect(Collectors.toList());
      if (fileList.isEmpty()) {
        logUnresolvedFile("The file name '{}' of the coverage report can not be resolved, the file does not exist in all <source>.", filename);
        return null;
      }
      if (fileList.size() > 1) {
        logUnresolvedFile("Ambigous file name '{}' detected in mulitple location of the coverage report <source>.", filename);
        return null;
      }
      absolutePath = fileList.get(0).getAbsolutePath();
    }
    return context.fileSystem().inputFile(context.fileSystem().predicates().hasAbsolutePath(absolutePath));
  }

  private void logUnresolvedFile(String msg, String filename) {
    unresolvedNb++;
    if (unresolvedNb == 1) {
      LOG.error(msg, filename);
    }
  }

}
