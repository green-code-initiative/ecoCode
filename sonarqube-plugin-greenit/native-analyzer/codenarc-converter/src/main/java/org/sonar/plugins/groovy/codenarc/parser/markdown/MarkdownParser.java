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
package org.sonar.plugins.groovy.codenarc.parser.markdown;

import org.apache.commons.io.IOUtils;
import org.codenarc.rule.AbstractRule;
import org.codenarc.ruleregistry.RuleRegistry;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.groovy.codenarc.RuleConverter;
import org.sonar.plugins.groovy.codenarc.Rule;
import org.sonar.plugins.groovy.codenarc.parser.RuleParser;
import org.sonar.plugins.groovy.codenarc.parser.RuleParserResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MarkdownParser implements RuleParser {

    /**
     * location of the markdown files in the CodeNarc project (see git submodule)
     */
    private static final String RULES_MARKDOWN_FILES_LOCATION = "CodeNarc/docs";

    private static final Logger log = LoggerFactory.getLogger(MarkdownParser.class);

    private final RuleRegistry registry;

    private final Parser parser;
    private final HtmlRenderer htmlRenderer;
    private final TextContentRenderer textRenderer;

    public MarkdownParser(RuleRegistry registry, Parser parser, HtmlRenderer htmlRenderer, TextContentRenderer textRenderer) {
        this.registry = registry;
        this.parser = parser;
        this.htmlRenderer = htmlRenderer;
        this.textRenderer = textRenderer;
    }

    @Override
    public RuleParserResult parse(Path source) throws IOException, ReflectiveOperationException {
        File docsFolder =  getSourceFolder(source).toFile();
        File[] docs = docsFolder.listFiles((dir, name) -> name.startsWith("codenarc-rules-"));
        if (docs == null) {
            throw new FileNotFoundException(String.format("%s does not contain any files matching pattern codenarc-rules-*", docsFolder.getAbsolutePath()));
        }
        String version =
                IOUtils.toString(
                        MarkdownParser.class.getResourceAsStream("/codenarc-version.txt"), StandardCharsets.UTF_8);

        RuleParserResult result = new RuleParserResult(version);

        Properties props = new Properties();
        props.load(RuleConverter.class.getResourceAsStream("/codenarc-base-messages.properties"));

        for (File doc : docs) {
            Node document = parser.parseReader(Files.newBufferedReader(doc.toPath()));
            RuleSetVisitor visitor = new RuleSetVisitor(registry, htmlRenderer, textRenderer);
            document.accept(visitor);

            Map<String, RuleDescription> parametersByRule = new HashMap<>();

            for (RuleDescription r : visitor.getRules()) {
                parametersByRule.put(r.getName(), r);
            }

            for (RuleDescription r : visitor.getRules()) {
                Class<?> clazz = registry.getRuleClass(r.getName());
                if (clazz != null) {
                    result.addRule(visitor.getRuleset(), new Rule(clazz.asSubclass(AbstractRule.class), r.getSince(), props, parametersByRule));
                } else {
                    log.warn("Could not find rule file for {}", r.getName());
                }
            }

        }
        return result;
    }

  public Path getSourceFolder(Path source) {
    return source.resolve(RULES_MARKDOWN_FILES_LOCATION);
  }
}
