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

import io.ecocode.xml.checks.XPathSimpleCheck;
import org.sonar.check.Rule;

/**
 * Checks in theme xmls and manifest if the application override a "light" theme:
 * <ul>
 *     <li>Check styles that inherit Theme.Holo.Light or Theme.AppCompat.Light themes (or other themes that begin by
 *     Theme.Holo.Light or Theme.AppCompat.Light)</li>
 *     <li>Check themes set in manifest that begin by "@android:style/Theme.Holo.Light" or "@style/Theme.AppCompat.Light"
 *     </li>
 * </ul>
 */
@Rule(key = "ESOB004", name = "ecoCodeDarkUITheme")
public class DarkUIThemeXmlRule extends XPathSimpleCheck {

    private static final String STYLE_HOLO_LIGHT = "/resources/style[starts-with(@parent, \"@android:style/Theme.Holo.Light\")]/@parent";
    private static final String STYLE_APPCOMPAT_LIGHT = "/resources/style[starts-with(@parent, \"Theme.AppCompat.Light\")]/@parent";
    private static final String MANIFEST_HOLO_LIGHT = "/manifest/application[starts-with(@theme, \"@android:style/Theme.Holo.Light\")]/@theme";
    private static final String MANIFEST_APPCOMPAT_LIGHT = "/manifest/application[starts-with(@theme, \"@style/Theme.AppCompat.Light\")]/@theme";

    @Override
    protected String getMessage() {
        return "Using a light theme may have a significant impact on energy consumption on (AM)OLED screens.";
    }

    @Override
    protected String getXPathExpressionString() {
        return STYLE_HOLO_LIGHT + "|" + STYLE_APPCOMPAT_LIGHT + "|" + MANIFEST_HOLO_LIGHT + "|" + MANIFEST_APPCOMPAT_LIGHT;
    }
}
