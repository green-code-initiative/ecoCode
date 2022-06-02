/*
 * ecoCode SonarQube Plugin
 * Copyright (C) 2020-2021 Snapp' - Université de Pau et des Pays de l'Adour
 * mailto: contact@ecocode.io
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
package io.ecocode.xml.checks.sobriety;

import io.ecocode.xml.checks.XPathCheck;
import org.sonar.check.Rule;
import org.w3c.dom.Node;

/**
 * Checks colors xml declaration. Two case.
 * Case 1:
 * <ul>
 *     <li>Checks for a list of given attribute that may contain a color in all xml nodes</li>
 *     <li>If the value is an hexa value >= to a given brightness threshold throw an issue</li>
 * </ul>
 * Case 2:
 *  <ul>
 *      <li>Checks the <resources><color> tag</li>
 *      <li>If the value is an hexa value >= to a given brightness threshold throw an issue</li>
 *  </ul>
 */
@Rule(key = "ESOB003", name = "ecocodeCheckColorXml")
public class DarkUIBrightColorsXmlRule extends XPathCheck {

    private static final String[] ATTRIBUTES_TO_CHECK = {
            "background",
            "foreground",
            "foregroundTint",
            "tint",
            "src",
            "textColor",
            "color",
            "textColorHighlight",
            "textColorHint",
            "textColorLink",
            "shadowColor",
            "srcCompat"
    };
    private static final String XPATH_COLOR_RESOURCES = "//resources/color/text()"; //NOSONAR - not a URI
    private static final String ERROR_MESSAGE = "Avoid using too bright colors for (AM)OLED screens.";
    private static final float DARKNESS_CAP_VALUE = 0.6f;

    @Override
    protected String getMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    protected String getXPathExpressionString() {
        StringBuilder xPathExpressionSelectAttributes = new StringBuilder();
        for (String attribute : ATTRIBUTES_TO_CHECK) {
            xPathExpressionSelectAttributes.append("//*[@" + attribute + "]/@" + attribute);
            xPathExpressionSelectAttributes.append("|");
        }
        return xPathExpressionSelectAttributes.toString() + XPATH_COLOR_RESOURCES;
    }

    @Override
    protected void visitNode(Node node, String message) {
        checkColorInput(node, message);
    }


    private void checkColorInput(Node attribute, String message) {
        String hexValue = attribute.getNodeValue();
        if (hexValue.startsWith("#")) {
            checkDarkColor(hexValue, attribute, message);
        }
    }

    private void checkDarkColor(String hexValue, Node nodeToReport, String message) {
        hexValue = hexValue.substring(1);
        int colorHex = (int) Long.parseLong(hexValue, 16);
        int r = (colorHex >> 16) & 0xFF;
        int g = (colorHex >> 8) & 0xFF;
        int b = (colorHex >> 0) & 0xFF;
        double darkness = 1 - (0.299 * r + 0.587 * g + 0.114 * b) / 255;
        if (darkness < DARKNESS_CAP_VALUE) {
            reportIssue(nodeToReport, message);
        }
    }
}
