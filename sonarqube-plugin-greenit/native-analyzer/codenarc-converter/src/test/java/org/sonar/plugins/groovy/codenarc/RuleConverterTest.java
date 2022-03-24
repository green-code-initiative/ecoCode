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
package org.sonar.plugins.groovy.codenarc;

import static org.junit.Assume.assumeTrue;

import com.google.common.collect.Lists;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.groovy.codenarc.parser.RuleParserResult;
import org.sonar.plugins.groovy.codenarc.printer.XMLPrinter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RuleConverterTest {

  private static final Logger log = LoggerFactory.getLogger(RuleConverterTest.class);

  private static final String PLUGIN_RULES_FILE_LOCATION =
      "../sonar-groovy-plugin/src/main/resources/org/sonar/plugins/groovy/rules.xml";

  @org.junit.Rule public TemporaryFolder tmpDir = new TemporaryFolder();

  @Test
  public void testXmlEquivalence() throws Exception {
    // Only run this test if CodeNarc was put in the correct location (this is guaranteed by a Git
    // submodule)
    Path codeNarcPath = Paths.get(".");
    assumeTrue(Files.exists(RuleConverter.createParser().getSourceFolder(codeNarcPath)));
    assertSimilarXml(getGeneratedXmlRulesFile(codeNarcPath), Paths.get(PLUGIN_RULES_FILE_LOCATION));
  }

  static void showDelta(String ruleName, String s1, String s2) {
    showDelta(ruleName, Lists.newArrayList(s1), Lists.newArrayList(s2));
  }

  static void showDelta(String ruleName, List<String> s1, List<String> s2) {
    log.info(
        "------------------------------------------------------------------------------------------");
    log.info("DIFFERENCE in {}", ruleName);
    Patch<String> p = DiffUtils.diff(s1, s2);
    for (Delta<String> delta : p.getDeltas()) {
      log.info("{}", delta);
    }
  }

  private Path getGeneratedXmlRulesFile(Path codeNarcDir) throws Exception {
    Path generatedRules = tmpDir.newFile("rules.xml").toPath();
    RuleParserResult result = RuleConverter.createParser().parse(codeNarcDir);
    new XMLPrinter().process(result).printAll(generatedRules);
    return generatedRules;
  }

  private static void assertSimilarXml(Path generatedRulesXML, Path rulesFromPluginXML)
      throws IOException, ParserConfigurationException, SAXException {
    int nbrDiff = 0;
    int nbrMissing = 0;

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    DocumentBuilder dBuilder = dbf.newDocumentBuilder();

    Document generatedDoc = dBuilder.parse(generatedRulesXML.toFile());
    Document pluginDoc = dBuilder.parse(rulesFromPluginXML.toFile());

    NodeList generatedNodes = generatedDoc.getChildNodes().item(1).getChildNodes();
    NodeList pluginNodes = pluginDoc.getChildNodes().item(1).getChildNodes();

    for (int i = 0; i < generatedNodes.getLength(); i++) {
      Node generatedRule = generatedNodes.item(i);
      Node pluginRule = null;
      short generatedNodeType = generatedRule.getNodeType();
      if (generatedNodeType != Node.COMMENT_NODE && generatedNodeType != Node.TEXT_NODE) {
        boolean found = false;
        boolean diff = false;
        for (int j = 0; j < pluginNodes.getLength(); j++) {
          pluginRule = pluginNodes.item(j);
          short pluginNodeType = pluginRule.getNodeType();
          if (pluginNodeType != Node.COMMENT_NODE && pluginNodeType != Node.TEXT_NODE) {
            if (generatedRule.isEqualNode(pluginRule)) {
              found = true;
              break;
            } else if (getRuleKey(generatedRule).equals(getRuleKey(pluginRule))) {
              diff = true;
              break;
            }
          }
        }
        if (diff) {
          nbrDiff++;
          String generatedRuleString = nodeToString(generatedRule);
          String pluginRuleString = nodeToString(pluginRule);
          showDelta(
              getRuleKey(generatedRule),
              Lists.newArrayList(generatedRuleString.split("\\r?\\n")),
              Lists.newArrayList(pluginRuleString.split("\\r?\\n")));
        } else if (!found) {
          nbrMissing++;
          log.info(
              "------------------------------------------------------------------------------------------");
          log.info("NOT FOUND: {}", getRuleKey(generatedRule));
        }
      }
    }

    Assert.assertEquals(0, nbrMissing);
    Assert.assertEquals(0, nbrDiff);
  }

  private static String getRuleKey(Node rule) {
    return rule.getChildNodes().item(1).getFirstChild().getNodeValue();
  }

  private static String nodeToString(Node node) {
    StringWriter sw = new StringWriter();
    try {
      TransformerFactory tf = TransformerFactory.newInstance();
      tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
      Transformer t = tf.newTransformer();
      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      t.setOutputProperty(OutputKeys.INDENT, "yes");
      t.transform(new DOMSource(node), new StreamResult(sw));
    } catch (TransformerException te) {
      log.error("nodeToString Transformer Exception", te);
    }
    return sw.toString();
  }
}
