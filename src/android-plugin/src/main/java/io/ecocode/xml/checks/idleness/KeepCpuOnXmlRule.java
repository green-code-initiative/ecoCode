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
package io.ecocode.xml.checks.idleness;

import io.ecocode.xml.checks.helpers.CheckPermissionsRule;
import org.sonar.check.Rule;

/**
 * Checks manifest uses-permissions statement.
 * if the permissions "android.permission.WAKE_LOCK" is found, report an issue.
 */
@Rule(key = "EIDL005", name = "ecocodeKeepCpuOnXml")
public class KeepCpuOnXmlRule extends CheckPermissionsRule {

    private static final String PERMISSION_NAME = "android.permission.WAKE_LOCK";
    private static final String ERROR_MESSAGE = "Keeping the screen on should be avoided to avoid draining battery.";

    public KeepCpuOnXmlRule() {
        super(PERMISSION_NAME, ERROR_MESSAGE);
    }
}