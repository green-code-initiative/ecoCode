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
package org.elegoff.plugins.communityrust.coverage.lcov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.CheckForNull;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.coverage.NewCoverage;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import static org.elegoff.plugins.communityrust.coverage.lcov.LCOVFields.BRDA;
import static org.elegoff.plugins.communityrust.coverage.lcov.LCOVFields.DA;
import static org.elegoff.plugins.communityrust.coverage.lcov.LCOVFields.SF;

public class LCOVParser {

  private static final Logger LOG = Loggers.get(LCOVParser.class);
  private final Map<InputFile, NewCoverage> fileCoverage;
  private final SensorContext sensorContext;
  private final Set<String> unknownPaths = new LinkedHashSet<>();
  private final FileChooser fc;
  private int pbCount = 0;

  private LCOVParser(List<String> lines, SensorContext sensorContext, FileChooser fc) {
    this.sensorContext = sensorContext;
    this.fc = fc;
    this.fileCoverage = parse(lines);
  }

  static LCOVParser build(SensorContext context, List<File> files, FileChooser fileChooser) {
    final List<String> lines;
    lines = new LinkedList<>();
    for (int i = 0, filesSize = files.size(); i < filesSize; i++) {
      var file = files.get(i);
      try (Stream<String> fileLines = Files.lines(file.toPath())) {
        lines.addAll(fileLines.collect(Collectors.toList()));
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not read content from file: " + file, e);
      }
    }
    return new LCOVParser(lines, context, fileChooser);
  }

  Map<InputFile, NewCoverage> getFileCoverage() {
    return fileCoverage;
  }

  List<String> unknownPaths() {
    return new ArrayList<>(unknownPaths);
  }

  int pbCount() {
    return pbCount;
  }

  private Map<InputFile, NewCoverage> parse(List<String> lines) {
    final Map<InputFile, FileContent> files = new HashMap<>();
    FileContent fileContent = null;
    var reportLineNum = 0;

    for (String line : lines) {
      reportLineNum++;
      if (line.startsWith(String.valueOf(SF))) {
        fileContent = files.computeIfAbsent(inputFileForSourceFile(line),
          inputFile -> inputFile == null ? null : new FileContent(inputFile));

      } else if (fileContent != null) {
        if (line.startsWith(String.valueOf(DA))) {
          parseLineCoverage(fileContent, reportLineNum, line);

        } else if (line.startsWith(String.valueOf(BRDA))) {
          parseBranchCoverage(fileContent, reportLineNum, line);
        }
      }

    }

    Map<InputFile, NewCoverage> coveredFiles = new HashMap<>();

    files.entrySet().forEach(e -> {
      var newCoverage = sensorContext.newCoverage().onFile(e.getKey());
      e.getValue().save(newCoverage);
      coveredFiles.put(e.getKey(), newCoverage);
    });
    return coveredFiles;
  }

  private void parseBranchCoverage(FileContent fileContent, int linum, String line) {
    try {
      String[] tokens = line.substring(String.valueOf(BRDA).length() + 1).trim().split(",");
      String lineNumber = tokens[0];
      String branchNumber = tokens[1] + tokens[2];
      String taken = tokens[3];

      fileContent.newBranch(Integer.valueOf(lineNumber), branchNumber, "-".equals(taken) ? 0 : Integer.valueOf(taken));
    } catch (Exception e) {
      logMismatch(String.valueOf(BRDA), linum, e);
    }
  }

  private void parseLineCoverage(FileContent fileContent, int linum, String line) {
    try {
      var execution = line.substring(String.valueOf(DA).length() + 1);
      var executionCount = execution.substring(execution.indexOf(',') + 1);
      var lineNumber = execution.substring(0, execution.indexOf(','));

      fileContent.newLine(Integer.valueOf(lineNumber), Integer.valueOf(executionCount));
    } catch (Exception e) {
      logMismatch(String.valueOf(DA), linum, e);
    }
  }

  private void logMismatch(String dataType, int linum, Exception e) {
    LOG.debug(String.format("Error while parsing LCOV report: can't save %s data for line %s of coverage report file (%s).", dataType, linum, e.toString()));
    pbCount++;
  }

  @CheckForNull
  private InputFile inputFileForSourceFile(String line) {
    String filePath;
    filePath = line.substring(String.valueOf(SF).length() + 1);
    var inputFile = sensorContext.fileSystem().inputFile(sensorContext.fileSystem().predicates().hasPath(filePath));

    if (inputFile == null) {
      inputFile = fc.getInputFile(filePath);
    }

    if (inputFile == null) {
      unknownPaths.add(filePath);
    }

    return inputFile;
  }

  private static class FileContent {
    private static final String WRONG_LINE_MSG = "Line number %s doesn't exist in file %s";
    private final int linesInFile;
    private final String filename;
    private final Map<Integer, Map<String, Integer>> branches = new HashMap<>();
    private final Map<Integer, Integer> hits = new HashMap<>();

    FileContent(InputFile inputFile) {
      linesInFile = inputFile.lines();
      filename = inputFile.filename();
    }

    private void validateLine(Integer linum) {
      if (linum >= 1 && linum <= linesInFile) {
        return;
      }
      throw new IllegalArgumentException(String.format(WRONG_LINE_MSG, linum, filename));
    }

    void newLine(Integer lineNumber, Integer executionCount) {
      validateLine(lineNumber);
      hits.merge(lineNumber, executionCount, Integer::sum);
    }

    void newBranch(Integer lineNumber, String branchNumber, Integer taken) {
      validateLine(lineNumber);
      Map<String, Integer> branchesForLine = branches.computeIfAbsent(lineNumber, l -> new HashMap<>());
      branchesForLine.merge(branchNumber, taken, Integer::sum);
    }

    void save(NewCoverage newCoverage) {
      for (Map.Entry<Integer, Integer> e : hits.entrySet()) {
        newCoverage.lineHits(e.getKey(), e.getValue());
      }
      for (Map.Entry<Integer, Map<String, Integer>> e : branches.entrySet()) {
        int line = e.getKey();
        int conditions = e.getValue().size();
        var covered = 0;
        for (Integer taken : e.getValue().values()) {
          if (taken > 0) {
            covered++;
          }
        }

        newCoverage.conditions(line, conditions, covered);
        newCoverage.lineHits(line, hits.getOrDefault(line, 0) + covered);
      }
    }

  }

}
