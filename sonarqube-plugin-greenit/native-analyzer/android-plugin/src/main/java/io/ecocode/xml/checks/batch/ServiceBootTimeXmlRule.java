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
package io.ecocode.xml.checks.batch;

import io.ecocode.xml.checks.XPathSimpleCheck;
import org.sonar.check.Rule;

/**
 * Checks manifest intent-filter statement: if the action is BOOT_COMPLETED, report a bad practice
 */
@Rule(key = "EBAT001", name = "ecoServiceBootTimeXml")
public class ServiceBootTimeXmlRule extends XPathSimpleCheck {

    private static final String SERVICE_BOOT_TIME_ATTRIBUTE = "//manifest/application/receiver/intent-filter/action/@name[. = \"android.intent.action.BOOT_COMPLETED\"]";
    private static final String ERROR_MESSAGE = "Avoid using a receiver to launch a service with BOOT_COMPLETED to drain less battery";

    @Override
    protected String getMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    protected String getXPathExpressionString() {
        return SERVICE_BOOT_TIME_ATTRIBUTE;
    }
}
