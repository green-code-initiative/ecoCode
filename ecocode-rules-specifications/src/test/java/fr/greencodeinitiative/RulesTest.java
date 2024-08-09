/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2024 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sonar.api.SonarRuntime;
import org.sonar.api.issue.impact.SoftwareQuality;
import org.sonar.api.rule.RuleScope;
import org.sonar.api.rules.CleanCodeAttribute;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RuleDescriptionSection;
import org.sonar.api.server.rule.RuleDescriptionSection.RuleDescriptionSectionKeys;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;
import org.sonar.api.server.rule.internal.DefaultNewRepository;
import org.sonar.api.utils.Version;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RulesTest {
    private static final Version SONAR_API_VERSION = Version.create(10, 10);
    private static final String RESOURCE_DIR = "rules";
    private static final String RESOURCE_BASE_PATH = "/io/ecocode/" + RESOURCE_DIR + "/";
    private static final String[] LANGUAGES =
            new String[] {"csharp", "html", "java", "javascript", "php", "python", "swift", "xml"};

    private static final List<String> MANDATORY_TAGS =
            Arrays.asList("performance", "ecocode", "eco-design");

    private static final List<String> TECHNOLOGY_TAGS =
            Arrays.asList("android", "ios", "spring", "react-native", "sql", "regex", "memory");

    /**
     * Built-in rule tags from
     * https://docs.sonarsource.com/sonarqube/latest/user-guide/rules/built-in-rule-tags/
     */
    private static final List<String> BUILT_IN_RULE_TAGS = Arrays.asList("bad-practice");

    /** Tags from https://github.com/cnumr/best-practices-mobile */
    private static final List<String> BEST_PRACTISES_MOBILE_TAGS =
            Arrays.asList(
                    "optimized-api",
                    "leakage",
                    "bottleneck",
                    "idleness",
                    "power",
                    "batch",
                    "release",
                    "sobriety",
                    "longevity");

    /**
     * Let's not have too many tags, especially tags redundant with eco-design or performance, e.g.
     * do not allow environment.
     */
    private static final List<String> ALL_TAGS =
            Arrays.asList(
                            MANDATORY_TAGS,
                            BUILT_IN_RULE_TAGS,
                            TECHNOLOGY_TAGS,
                            BEST_PRACTISES_MOBILE_TAGS)
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

    private static final Pattern NONCOMPLIANT_COMMENT_PATTERN =
            Pattern.compile(
                    ".*(// Noncompliant|<!-- Noncompliant|# Noncompliant).*", Pattern.DOTALL);

    private static SonarRuntime sonarRuntime;

    @BeforeAll
    static void init() {
        sonarRuntime = mock(SonarRuntime.class);
        when(sonarRuntime.getApiVersion()).thenReturn(SONAR_API_VERSION);
    }

    private static Path[] findAllLangs() throws URISyntaxException, IOException {
        URI uri = RuleMetadataLoader.class.getResource(RESOURCE_BASE_PATH).toURI();
        Path path = Paths.get(uri);
        try (Stream<Path> walk = Files.walk(path, 1)) {
            return walk.filter(p -> !RESOURCE_DIR.equals(p.toFile().getName()))
                    .toArray(Path[]::new);
        }
    }

    private static SortedSet<String> findAllRules(Path langPath) throws IOException {
        SortedSet<String> rules = new TreeSet<>();
        try (Stream<Path> langWalk = Files.walk(langPath, 1)) {
            for (Iterator<Path> it = langWalk.iterator(); it.hasNext(); ) {
                String ruleFile = it.next().toFile().getName();
                int extensionIndex = ruleFile.lastIndexOf('.');
                if (extensionIndex != -1) {
                    String rule = ruleFile.substring(0, extensionIndex);
                    rules.add(rule);
                }
            }
        }
        return rules;
    }

    @Test
    void testRules() throws URISyntaxException, IOException {
        RulesDefinition.Context context = new RulesDefinition.Context();
        Path[] langPaths = findAllLangs();
        assertArrayEquals(LANGUAGES, Stream.of(langPaths).map(p -> p.toFile().getName()).toArray());
        for (Path langPath : langPaths) {
            String lang = langPath.toFile().getName();
            DefaultNewRepository repository = new DefaultNewRepository(context, lang, lang, false);
            RuleMetadataLoader ruleMetadataLoader =
                    new RuleMetadataLoader(RESOURCE_BASE_PATH + lang, sonarRuntime);
            ruleMetadataLoader.addRulesByRuleKey(
                    repository, new ArrayList<>(findAllRules(langPath)));
            context.registerRepository(repository);
        }
        assertEquals(LANGUAGES.length, context.repositories().size());
        for (Repository repo : context.repositories()) {
            assertFalse(repo.rules().isEmpty(), repo.key() + " repository should have rules");
            for (Rule rule : repo.rules()) {
                assertFalse(
                        rule.activatedByDefault(), rule + " should not be activated by default");
                assertEquals(RuleScope.MAIN, rule.scope(), rule + " should have MAIN scope");
                assertTrue(
                        rule.tags().containsAll(MANDATORY_TAGS),
                        rule + " should have tags " + MANDATORY_TAGS);

                assertTrue(
                        ALL_TAGS.containsAll(rule.tags()),
                        rule + " should only have allowed tags but got: " + rule.tags());
                assertEquals(
                        RuleType.CODE_SMELL, rule.type(), rule + " should have CODE_SMELL type");
                assertEquals(
                        Collections.singleton(SoftwareQuality.RELIABILITY),
                        rule.defaultImpacts().keySet(),
                        rule + " should have RELIABILITY Software Quality");
                assertEquals(
                        CleanCodeAttribute.EFFICIENT,
                        rule.cleanCodeAttribute(),
                        rule + " should have EFFICIENT Clean Code Attribute");
                // See guidelines in
                // https://docs.sonarsource.com/sonarqube/latest/extension-guide/adding-coding-rules/#coding-rule-guidelines
                assertTrue(
                        rule.name().contains(" should "),
                        rule + " name should contain 'should': " + rule.name());
                assertFalse(
                        rule.name().endsWith("."),
                        rule + " name should not end with a point: " + rule.name());
                // Avoid rules such as "Idleness: Keep Screen On (addFlags)"
                assertFalse(
                        rule.name().contains(":"),
                        rule + " names should not have a prefix tag: " + rule.name());

                List<String> sections =
                        rule.ruleDescriptionSections().stream()
                                .map(RuleDescriptionSection::getKey)
                                .collect(Collectors.toList());
                // see https://docs.asciidoctor.org/asciidoc/latest/sections/auto-ids/
                assertFalse(
                        sections.isEmpty(),
                        rule
                                + " should have standard description sections but gone none (is"
                                + " :!sectids: present?)");
                // Introduction is optional
                if (RuleDescriptionSectionKeys.INTRODUCTION_SECTION_KEY.equals(sections.get(0))) {
                    sections.remove(0);
                }
                assertEquals(
                        Arrays.asList(
                                RuleDescriptionSectionKeys.ROOT_CAUSE_SECTION_KEY,
                                RuleDescriptionSectionKeys.HOW_TO_FIX_SECTION_KEY,
                                RuleDescriptionSectionKeys.RESOURCES_SECTION_KEY),
                        sections,
                        rule + " should have standard description sections");
                String howToFix =
                        rule.ruleDescriptionSections().stream()
                                .filter(
                                        s ->
                                                RuleDescriptionSectionKeys.HOW_TO_FIX_SECTION_KEY
                                                        .equals(s.getKey()))
                                .findFirst()
                                .orElse(null)
                                .getHtmlContent();
                assertTrue(
                        NONCOMPLIANT_COMMENT_PATTERN.matcher(howToFix).matches(),
                        rule
                                + " must have Noncompliant comment in the How to fix section: "
                                + howToFix);
                assertTrue(
                        howToFix.contains("<h3>Noncompliant code example</h3>"),
                        rule
                                + " must have <h3>Noncompliant code example</h3> in How to fix"
                                + " section: "
                                + howToFix);
                assertTrue(
                        howToFix.contains("<h3>Compliant solution</h3>"),
                        rule
                                + " must have <h3>Compliant solution</h3> in How to fix section: "
                                + howToFix);
            }
        }
    }
}
