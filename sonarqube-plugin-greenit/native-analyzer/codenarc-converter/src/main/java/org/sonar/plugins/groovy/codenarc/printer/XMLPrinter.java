/*
 * Sonar CodeNarc Converter
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
package org.sonar.plugins.groovy.codenarc.printer;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.groovy.codenarc.*;
import org.sonar.plugins.groovy.codenarc.parser.RuleParserResult;

public final class XMLPrinter {

  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private String resultAsXML;

  public XMLPrinter process(RuleParserResult result) throws IOException {
    StringBuilder xmlStringBuilder = new StringBuilder();


    xmlStringBuilder.append("<!-- Generated using CodeNarc " + result.getCodeNarcVersion() + " -->");
    xmlStringBuilder.append(LINE_SEPARATOR);

    start(xmlStringBuilder);

    for (RuleSet ruleSet : RuleSet.values()) {
      startSet(xmlStringBuilder, ruleSet.getLabel());
      ArrayList<Rule> rules = Lists.newArrayList(result.getRulesBy(ruleSet));
      for (Rule rule : rules) {
        printAsXML(rule, xmlStringBuilder);
      }
    }
    end(xmlStringBuilder);
    this.resultAsXML = xmlStringBuilder.toString();
    return this;
  }

  public String generatedXML() {
    return resultAsXML;
  }

  public void printAll(Path resultFile) throws IOException {
    try (Writer out = Files.newBufferedWriter(resultFile)) {
      out.write(resultAsXML);
    }
  }

  private static void startSet(StringBuilder xmlStringBuilder, String name) {
    xmlStringBuilder.append("  <!-- " + name + " rules -->");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append(LINE_SEPARATOR);
  }

  private static void start(StringBuilder xmlStringBuilder) {
    xmlStringBuilder.append("<rules>");
    xmlStringBuilder.append(LINE_SEPARATOR);
  }

  private static void end(StringBuilder xmlStringBuilder) {
    xmlStringBuilder.append("</rules>");
    xmlStringBuilder.append(LINE_SEPARATOR);
  }

  /** Rule format based on {@link org.sonar.api.server.rule.RulesDefinitionXmlLoader} */
  private static void printAsXML(Rule rule, StringBuilder xmlStringBuilder) {
    if (rule.version != null) {
      xmlStringBuilder.append("  <!-- since " + rule.version + " -->");
      xmlStringBuilder.append(LINE_SEPARATOR);
    }
    xmlStringBuilder.append("  <rule>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append("    <key>" + rule.fixedRuleKey() + "</key>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append("    <severity>" + rule.severity + "</severity>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append("    <name><![CDATA[" + rule.name + "]]></name>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append("    <internalKey><![CDATA[" + rule.internalKey + "]]></internalKey>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append("    <description><![CDATA[" + rule.description + "]]></description>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    if (!rule.tags.isEmpty()) {
      for (String tag : rule.tags) {
        xmlStringBuilder.append("    <tag>" + tag + "</tag>");
        xmlStringBuilder.append(LINE_SEPARATOR);
      }
    }

    if (!rule.parameters.isEmpty()) {
      List<RuleParameter> sortedParameters = Lists.newArrayList(rule.parameters);
      Collections.sort(sortedParameters);
      for (RuleParameter parameter : sortedParameters) {
        xmlStringBuilder.append("    <param>");
        xmlStringBuilder.append(LINE_SEPARATOR);
        xmlStringBuilder.append("      <key>" + parameter.key() + "</key>");
        xmlStringBuilder.append(LINE_SEPARATOR);
        if (StringUtils.isNotBlank(parameter.description())) {
          xmlStringBuilder.append(
              "      <description><![CDATA[" + parameter.description() + "]]></description>");
          xmlStringBuilder.append(LINE_SEPARATOR);
        }
        if (StringUtils.isNotBlank(parameter.defaultValue())
            && !"null".equals(parameter.defaultValue())) {
          xmlStringBuilder.append(
              "      <defaultValue>" + parameter.defaultValue() + "</defaultValue>");
          xmlStringBuilder.append(LINE_SEPARATOR);
        }
        xmlStringBuilder.append("    </param>");
        xmlStringBuilder.append(LINE_SEPARATOR);
      }
    }

    xmlStringBuilder.append("  </rule>");
    xmlStringBuilder.append(LINE_SEPARATOR);
    xmlStringBuilder.append(LINE_SEPARATOR);
  }
}
