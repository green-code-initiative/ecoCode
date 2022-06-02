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
package org.sonar.plugins.groovy.codenarc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.codenarc.analyzer.AbstractSourceAnalyzer;
import org.codenarc.results.DirectoryResults;
import org.codenarc.results.FileResults;
import org.codenarc.results.Results;
import org.codenarc.rule.Violation;
import org.codenarc.ruleset.RuleSet;
import org.codenarc.source.SourceString;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class CodeNarcSourceAnalyzer extends AbstractSourceAnalyzer {
  private static final Logger LOG = Loggers.get(CodeNarcSourceAnalyzer.class);

  private final Map<InputFile, List<Violation>> violationsByFile = new HashMap<>();
  private final List<InputFile> sourceFiles;

  public CodeNarcSourceAnalyzer(List<InputFile> sourceFiles) {
    this.sourceFiles = sourceFiles;
  }

  @Override
  public Results analyze(RuleSet ruleSet) {
    List<FileResults> resultsByFile = processFiles(ruleSet);
    DirectoryResults directoryResults = new DirectoryResults(".");
    resultsByFile.forEach(directoryResults::addChild);
    return directoryResults;
  }

  private List<FileResults> processFiles(RuleSet ruleSet) {
    List<FileResults> results = new LinkedList<>();
    for (InputFile inputFile : sourceFiles) {
      try {
        List<Violation> violations = collectViolations(new SourceString(inputFile.contents()), ruleSet);
        violationsByFile.put(inputFile, violations);
        FileResults result = new FileResults(inputFile.uri().toString(), violations);
        results.add(result);
      } catch (IOException e) {
        LOG.error("Could not read input file: " + inputFile.toString(), e);
      }
    }
    return results;
  }

  @Override
  public List<?> getSourceDirectories() {
    return new ArrayList<>();
  }

  public Map<InputFile, List<Violation>> getViolationsByFile() {
    return violationsByFile;
  }

}
