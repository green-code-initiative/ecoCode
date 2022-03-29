/*
 * Sonar Groovy Plugin
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
package org.sonar.plugins.groovy.jacoco;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.data.IExecutionDataVisitor;
import org.jacoco.core.data.ISessionInfoVisitor;
import org.jacoco.core.data.SessionInfoStore;

/**
 * Utility class to merge JaCoCo reports.
 *
 * <p>This class handles two versions of JaCoCo binary format to merge.
 */
public class JaCoCoReportMerger {

  private JaCoCoReportMerger() {}

  /**
   * Merge all reports in reportOverall.
   *
   * @param reportOverall destination file of merge.
   * @param reports files to be merged.
   */
  public static void mergeReports(Path reportOverall, File... reports) {
    SessionInfoStore infoStore = new SessionInfoStore();
    ExecutionDataStore dataStore = new ExecutionDataStore();
    loadSourceFiles(infoStore, dataStore, reports);

    try (OutputStream fos = Files.newOutputStream(reportOverall);
        BufferedOutputStream outputStream = new BufferedOutputStream(fos)) {
      Object visitor;
      visitor = new ExecutionDataWriter(outputStream);
      infoStore.accept((ISessionInfoVisitor) visitor);
      dataStore.accept((IExecutionDataVisitor) visitor);
    } catch (IOException e) {
      throw new IllegalStateException(
          String.format("Unable to write overall coverage report %s", reportOverall), e);
    }
  }

  private static void loadSourceFiles(
      ISessionInfoVisitor infoStore, IExecutionDataVisitor dataStore, File... reports) {
    for (File report : reports) {
      if (report.isFile()) {
        new JaCoCoReportReader(report).readJacocoReport(dataStore, infoStore);
      }
    }
  }
}
