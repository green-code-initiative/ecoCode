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
package io.ecocode.java.checks.optimized_api;

import com.google.common.collect.ImmutableList;
import io.ecocode.java.checks.helpers.TreeHelper;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ImportTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Checks the import of android.location and com.google.android.gms.location.*</li>
 * <li>If the first import is found but not the second, report an issue.</li>
 * </ul>`
 */
@Rule(key = "EOPT001", name = "ecoCodeFusedLocation")
public class FusedLocationRule extends IssuableSubscriptionVisitor {

    protected static final String WRONG_IMPORT = "android.location";
    protected static final String GOOD_IMPORT = "com.google.android.gms.location";
    private boolean hasSeenWrongImport = false;
    private boolean hasSeenGoodImport = false;
    private ArrayList<ImportTree> mImportTreeList = new ArrayList<>();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.IMPORT);
    }

    @Override
    public void visitNode(Tree tree) {
        ImportTree importTree = (ImportTree) tree;
        String fullQualifiedImportName = TreeHelper.fullQualifiedName(importTree.qualifiedIdentifier());
        if (fullQualifiedImportName.startsWith(WRONG_IMPORT)) {
            hasSeenWrongImport = true;
            mImportTreeList.add(importTree);
        }
        if (fullQualifiedImportName.startsWith(GOOD_IMPORT)) {
            hasSeenGoodImport = true;
        }
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        super.leaveFile(context);
        if (hasSeenWrongImport && !hasSeenGoodImport) {
            for (ImportTree importTree : mImportTreeList) {
                reportIssue(importTree, "Use com.google.android.gms.location instead of android.location to maximize battery life.");
            }
        }

        hasSeenGoodImport = false;
        hasSeenWrongImport = false;
        mImportTreeList.clear();
    }
}
