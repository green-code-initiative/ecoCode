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

import org.codenarc.ruleregistry.RuleRegistry;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.sonar.plugins.groovy.codenarc.RuleSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleSetVisitor extends AbstractVisitor {

    private final RuleRegistry registry;

    private final HtmlRenderer htmlRenderer;
    private final TextContentRenderer textRenderer;

    private RuleSet ruleset;
    private final List<RuleDescription> rules = new ArrayList<>();

    public RuleSetVisitor(RuleRegistry registry, HtmlRenderer htmlRenderer, TextContentRenderer textContentRenderer) {
        this.registry = registry;

        this.htmlRenderer = htmlRenderer;
        this.textRenderer = textContentRenderer;
    }

    public RuleSet getRuleset() {
        return ruleset;
    }

    public List<RuleDescription> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public int getCount() {
        return rules.size();
    }

    @Override
    public void visit(Heading heading) {
        if (isRuleDescriptionSection(heading)) {
            RuleVisitor ruleVisitor = new RuleVisitor(htmlRenderer, textRenderer);
            heading.accept(ruleVisitor);
            rules.add(ruleVisitor.getRule());
        }
        visitChildren(heading);
    }

    @Override
    public void visit(Text text) {
        if (text.getLiteral().startsWith("rulesets/")) {
            ruleset = RuleSet.valueOf(text.getLiteral().replaceFirst("rulesets/(.*)\\.xml", "$1").toUpperCase());
        }
        visitChildren(text);
    }

    private boolean isRuleDescriptionSection(Heading heading) {
        if (heading.getLevel() == 2) {
            Node header = heading.getFirstChild();
            if (header.getClass().isAssignableFrom(Text.class)) {
                Text t = (Text) header;
                return registry.getRuleClass(t.getLiteral().split(" ")[0]) != null;
            }
        }
        return false;
    }
}
