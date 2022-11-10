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
package io.ecocode.xml.checks.helpers;

import io.ecocode.xml.checks.XPathCheck;
import org.w3c.dom.Node;

/**
 * Checks manifest uses-permissions statement.
 * If the manifest contains the permission parameter, report an issue.
 */
public abstract class CheckPermissionsRule extends XPathCheck {

    private final String xpathManifestExpression;
    private final String errorMessage;

    protected CheckPermissionsRule(String permissionName, String errorMessage) {
        this.xpathManifestExpression = "//manifest/uses-permission/@name[. = \"" + permissionName + "\"]";
        this.errorMessage = errorMessage;
    }

    @Override
    protected String getMessage() {
        return errorMessage;
    }

    @Override
    protected String getXPathExpressionString() {
        return xpathManifestExpression;
    }

    @Override
    protected void visitNode(Node node, String message) {
        reportIssue(node, getMessage());
    }
}
