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

import org.codenarc.ruleregistry.PropertiesFileRuleRegistry;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.groovy.codenarc.parser.RuleParser;
import org.sonar.plugins.groovy.codenarc.parser.RuleParserResult;
import org.sonar.plugins.groovy.codenarc.parser.markdown.MarkdownParser;
import org.sonar.plugins.groovy.codenarc.printer.XMLPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class RuleConverter {

    private static final Logger log = LoggerFactory.getLogger(RuleConverter.class);

    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        Path baseDir = Paths.get(".");
        if (args.length > 0) {
            baseDir = Paths.get(args[0]);
        }

        Path targetFile = getResultFile(baseDir);
        RuleParser parser = createParser();

        RuleParserResult result = parser.parse(baseDir);

        new XMLPrinter().process(result).printAll(targetFile);

        printResultsByCategory(result);
        printResultsByVersion(result);
        log.info("{} rules processed", result.getCount());
    }

    public static RuleParser createParser() {
        Parser parser = Parser.builder()
                .extensions(Arrays.asList(YamlFrontMatterExtension.create(), TablesExtension.create()))
                .build();

        HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(Collections.singletonList(TablesExtension.create())).build();
        TextContentRenderer textRenderer = TextContentRenderer.builder().extensions(Collections.singletonList(TablesExtension.create())).build();

        return new MarkdownParser(new PropertiesFileRuleRegistry(), parser, htmlRenderer, textRenderer);
    }


    private static Path getResultFile(Path baseDir) throws IOException {
        Path folder = baseDir.resolve("target/results/rules.xml");
        Files.createDirectories(folder.getParent());
        return folder;
    }

    private static void printResultsByVersion(RuleParserResult result) {
        log.info("Rules by Version:");
        result.getRuleCountByVersion().forEach((version, count) -> log.info("  - {} : {}", version, count));
    }

    private static void printResultsByCategory(RuleParserResult result) {
        log.info("Rules by category:");
        result.getRuleCountByCategory().forEach((category, count) -> log.info("  - {} : {}", category, count));
    }
}
