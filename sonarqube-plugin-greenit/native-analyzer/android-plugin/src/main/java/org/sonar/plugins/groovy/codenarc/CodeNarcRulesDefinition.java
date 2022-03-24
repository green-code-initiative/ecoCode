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
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.api.utils.MessageException;
import org.sonar.plugins.groovy.foundation.Groovy;

public class CodeNarcRulesDefinition implements RulesDefinition {

  public static final String REPOSITORY_KEY = Groovy.KEY;
  public static final String REPOSITORY_NAME = "CodeNarc";
  private static final String COST_FILE_PATH = "/org/sonar/plugins/groovy/cost.csv";

  @Override
  public void define(Context context) {
    NewRepository repository =
        context.createRepository(REPOSITORY_KEY, Groovy.KEY).setName(REPOSITORY_NAME);

    RulesDefinitionXmlLoader ruleLoader = new RulesDefinitionXmlLoader();
    ruleLoader.load(
        repository,
        CodeNarcRulesDefinition.class.getResourceAsStream("/org/sonar/plugins/groovy/rules.xml"),
        "UTF-8");
    addRemediationCost(repository.rules());
    repository.done();
  }

  private static void addRemediationCost(Collection<NewRule> rules) {
    Map<String, String> costByRule = getCostByRule();
    for (NewRule newRule : rules) {
      String ruleKey = newRule.key();
      if (costByRule.containsKey(ruleKey)) {
        DebtRemediationFunction linear =
            newRule.debtRemediationFunctions().linear(costByRule.get(ruleKey));
        newRule.setDebtRemediationFunction(linear);
      }
    }
  }

  private static Map<String, String> getCostByRule() {
    Map<String, String> result = new HashMap<>();
    List<String> lines;
    try {
      lines =
          IOUtils.readLines(
              CodeNarcRulesDefinition.class.getResourceAsStream(COST_FILE_PATH),
              StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw MessageException.of("Unable to load rules remediation function/factor", e);
    }

    lines.stream().skip(1).forEach(line -> CodeNarcRulesDefinition.completeCost(line, result));

    return result;
  }

  private static void completeCost(String line, Map<String, String> costByRule) {
    String[] blocks = line.split(";");
    String ruleKey = blocks[0];
    // block 1 contains the function (always linear)
    String ruleCost = blocks[2];
    costByRule.put(ruleKey, ruleCost);
  }
}
