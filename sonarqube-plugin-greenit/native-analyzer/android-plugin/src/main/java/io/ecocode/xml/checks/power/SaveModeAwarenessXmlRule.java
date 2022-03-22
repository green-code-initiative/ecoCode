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
package io.ecocode.xml.checks.power;

import io.ecocode.xml.checks.XPathSimpleCheck;
import org.sonar.check.Rule;

/**
 * Checks manifest uses-permissions statement:
 * if the permission "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" is found, report an issue.
 */
@Rule(key = "EPOW007", name = "ecocodeSaveModeAwarenessXml")
public class SaveModeAwarenessXmlRule extends XPathSimpleCheck {

    private static final String ACTION_POWER_CHANGED_XPATH = "//manifest/application/receiver/intent-filter/action/@name[.=\"android.intent.action.BATTERY_CHANGED\"]";
    private static final String ADVICE_MESSAGE = "Taking into account when the device is entering or exiting the power save mode is a good practice.";

    @Override
    protected String getMessage() {
        return ADVICE_MESSAGE;
    }

    @Override
    protected String getXPathExpressionString() {
        return ACTION_POWER_CHANGED_XPATH;
    }
}