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
package org.sonar.plugins.groovy.gmetrics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.gmetrics.GMetricsRunner;
import org.gmetrics.analyzer.SourceAnalyzer;
import org.gmetrics.ant.AntFileSetSourceAnalyzer;
import org.gmetrics.metric.cyclomatic.CyclomaticComplexityMetric;
import org.gmetrics.metric.linecount.ClassLineCountMetric;
import org.gmetrics.metric.linecount.MethodLineCountMetric;
import org.gmetrics.resultsnode.ClassResultsNode;
import org.gmetrics.resultsnode.PackageResultsNode;
import org.gmetrics.resultsnode.ResultsNode;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;

public class GMetricsSourceAnalyzer {

  private static final List<org.gmetrics.metric.Metric> GMETRICS =
      Arrays.asList(
          new CyclomaticComplexityMetric(),
          new ClassLineCountMetric(),
          new MethodLineCountMetric());

  private final Map<InputFile, List<ClassResultsNode>> resultsByFile = new HashMap<>();

  private final Map<String, InputFile> pathToInputFile = new HashMap<>();
  private final Set<File> files = new HashSet<>();

  private final File fileSystemBaseDir;

  public GMetricsSourceAnalyzer(FileSystem fileSystem, List<InputFile> sourceFiles) {
    this.fileSystemBaseDir = fileSystem.baseDir();

    for (InputFile inputFile : sourceFiles) {
      pathToInputFile.put(inputFile.absolutePath(), inputFile);
      files.add(inputFile.file());
    }
  }

  public Map<InputFile, List<ClassResultsNode>> resultsByFile() {
    return resultsByFile;
  }

  public void analyze() {
    FileSet fileSet = new FileSet();
    fileSet.setDir(fileSystemBaseDir);
    fileSet.add((basedir, filename, file) -> files.contains(file));

    List<FileSet> fileSets = new ArrayList<>();
    fileSets.add(fileSet);

    Project project = new Project();
    project.setBaseDir(fileSystemBaseDir);

    SourceAnalyzer analyzer = new AntFileSetSourceAnalyzer(project, fileSets);

    GMetricsRunner runner = new GMetricsRunner();
    runner.setMetricSet(() -> GMETRICS);
    runner.setSourceAnalyzer(analyzer);
    ResultsNode resultNode = runner.execute();

    processResults(resultNode, pathToInputFile);
  }

  private void processResults(ResultsNode resultNode, Map<String, InputFile> pathToInputFile) {
    if (resultNode instanceof PackageResultsNode) {
      processPackageResults((PackageResultsNode) resultNode, pathToInputFile);
    } else {
      processClassResults((ClassResultsNode) resultNode, pathToInputFile);
    }
  }

  private void processPackageResults(
      PackageResultsNode resultNode, Map<String, InputFile> pathToInputFile) {
    for (Entry<String, ResultsNode> entry : resultNode.getChildren().entrySet()) {
      processResults(entry.getValue(), pathToInputFile);
    }
  }

  private void processClassResults(
      ClassResultsNode resultNode, Map<String, InputFile> pathToInputFile) {
    String filePath = resultNode.getFilePath();
    InputFile inputFile = pathToInputFile.get(filePath);
    if (inputFile != null) {
      resultsByFile.putIfAbsent(inputFile, new ArrayList<>());
      resultsByFile.get(inputFile).add(resultNode);
    }
  }
}
