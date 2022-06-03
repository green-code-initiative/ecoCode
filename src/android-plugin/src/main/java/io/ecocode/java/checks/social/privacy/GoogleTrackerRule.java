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
package io.ecocode.java.checks.social.privacy;

import io.ecocode.java.checks.helpers.TreeHelper;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Check the presence of the import com.google.android.gms.analytics.Tracker
 * Importing the com.google.android.gms.analytics.Tracker class means that
 * the app sends hits to Google Analytics. It is not necessarily sensitive
 * information, but it is a first step towards Google Ads and hence this
 * practice should be discouraged at early stage.
 */
@Rule(key = "SPRI002", name = "ecocodeGoogleTracker")
public class GoogleTrackerRule extends BaseTreeVisitor implements JavaFileScanner {

  private static final String ERROR_MESSAGE_GGL_TRACKER = "Using com.google.android.gms.analytics.* is a potential threat for privacy.";
  private static final String ERROR_MESSAGE_FIREBASE_TRACKER = "Using com.google.firebase.analytics.* is a potential threat for privacy.";

  @Override
  public void scanFile(JavaFileScannerContext context) {
    CompilationUnitTree cut = context.getTree();

    GoogleTrackerImports googleTrackerImport = new GoogleTrackerImports();

    for (ImportClauseTree importClauseTree : cut.imports()) {
      ImportTree importTree = null;

      if (importClauseTree.is(Tree.Kind.IMPORT)) {
        importTree = (ImportTree) importClauseTree;
      }

      if (importTree == null) {
        // discard empty statements, which can be part of imports
        continue;
      }

      googleTrackerImport.collectTrackerImport(importTree);
    }

    handleResult(context, googleTrackerImport);
    scan(cut);
  }

  private void handleResult(JavaFileScannerContext context, GoogleTrackerImports googleTrackerImport) {
    if (googleTrackerImport.hasTrackerImports()) {
      if (googleTrackerImport.hasGoogleTrackerImports()) {
        for (ImportTree importTree : googleTrackerImport.getGoogleTrackerImports()) {
          context.reportIssue(this, importTree, ERROR_MESSAGE_GGL_TRACKER);
        }
      }

      if (googleTrackerImport.hasFirebaseTrackerImports()) {
        for (ImportTree importTree : googleTrackerImport.getFirebaseTrackerImports()) {
          context.reportIssue(this, importTree, ERROR_MESSAGE_FIREBASE_TRACKER);
        }
      }
    }
  }

  private class GoogleTrackerImports {
    private static final String IMPORT_STR_GGL_TRCK = "com.google.android.gms.analytics";
    private static final String IMPORT_STR_FIREBASE_TRCK = "com.google.firebase.analytics";

    private final List<ImportTree> gglTrListTree = new ArrayList<>();
    private final List<ImportTree> firebaseTrListTree = new ArrayList<>();

    public List<ImportTree> getGoogleTrackerImports() {
      return gglTrListTree;
    }

    public List<ImportTree> getFirebaseTrackerImports() {
      return firebaseTrListTree;
    }

    public boolean hasTrackerImports() {
      return hasGoogleTrackerImports() || hasFirebaseTrackerImports();
    }

    public boolean hasGoogleTrackerImports() {
      return !gglTrListTree.isEmpty();
    }

    public boolean hasFirebaseTrackerImports() {
      return !firebaseTrListTree.isEmpty();
    }

    public void collectTrackerImport(ImportTree importTree) {
      String importName = TreeHelper.fullQualifiedName(importTree.qualifiedIdentifier());
      if (importName.startsWith(IMPORT_STR_GGL_TRCK)) {
        gglTrListTree.add(importTree);
      } else if (importName.startsWith(IMPORT_STR_FIREBASE_TRCK)) {
        firebaseTrListTree.add(importTree);
      }
    }
  }
}
