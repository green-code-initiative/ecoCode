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

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableBody;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableRow;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class RuleVisitor extends AbstractVisitor {

    private RuleDescription rule;

    private final Renderer htmlRenderer;
    private final Renderer textRenderer;

    RuleVisitor(HtmlRenderer htmlRenderer, TextContentRenderer textRenderer) {
        this.htmlRenderer = htmlRenderer;
        this.textRenderer = textRenderer;
    }

    public RuleDescription getRule() {
        return rule;
    }

    @Override
    public void visit(Heading node) {
        rule = new RuleDescription();
        Text name = (Text) node.getFirstChild();
        rule.setName(name.getLiteral().split(" ")[0]);
        Text versionString = ((Text) node.getNext().getFirstChild().getFirstChild());
        if (versionString != null && versionString.getLiteral().startsWith("Since CodeNarc")) {
            rule.setSince(versionString.getLiteral().replaceAll("Since CodeNarc ([0-9.]+)(.*)", "$1").trim());
        }
        for (Node next = rule.getSince() == null ? node.getNext() : node.getNext().getNext(); isWithinSection(next, 2); next = next.getNext()) {
            if (isParametersTable(next)) {
                parseParameters((TableBlock) next);
            } else {
                rule.appendDescription(htmlRenderer.render(next));
            }
        }
    }

    private void parseParameters(TableBlock table) {
        TableBody body = (TableBody) table.getLastChild();
        TableRow row = (TableRow) body.getFirstChild();
        while (row != null) {
            TableCell propertyCell = ((TableCell) row.getFirstChild());
            TableCell descriptionCell = ((TableCell) row.getFirstChild().getNext());
            TableCell defaultValueCell = null;
            if (descriptionCell.getNext() != null) {
                defaultValueCell = (TableCell) descriptionCell.getNext();
            }

            String property = ((Text) propertyCell.getFirstChild()).getLiteral();
            String description = textRenderer.render(descriptionCell).trim();
            description = description.substring(0, description.length() - 1); // Strips last | character
            String defaultValue = defaultValueCell == null ? "" : textRenderer.render(defaultValueCell).trim();
            defaultValue = defaultValue.substring(0, defaultValue.length() - 1).replaceAll("\"", ""); // Strips last | character


            rule.addParameter(property, description, defaultValue);
            row = (TableRow) row.getNext();
        }

    }

    private boolean isParametersTable(Node node) {
        if (TableBlock.class.isAssignableFrom(node.getClass())) {
            TableBlock table = (TableBlock) node;
            return "Property".equals(((Text) table.getFirstChild().getFirstChild().getFirstChild().getFirstChild()).getLiteral());
        }
        return false;
    }

    private boolean isWithinSection(Node node, int level) {
        if (node == null) {
            return false;
        }
        if (Heading.class.isAssignableFrom(node.getClass())) {
            return ((Heading) node).getLevel() != level;
        }
        return true;
    }
}
