/**
 * Community Rust Plugin
 * Copyright (C) 2021 Eric Le Goff
 * mailto:community-rust AT pm DOT me
 * http://github.com/elegoff/sonar-rust
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
package org.elegoff.plugins.communityrust.clippy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONArray;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONObject;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.JSONParser;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.ParseException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClippyJsonReportReader {
  private static final String RESULTS = "results";
  private static final String BEGINJSON = "{\"" + RESULTS + "\": [";
  private static final String ENDJSON = "]}";
  private static final String VISIT_MSG = "for further information visit";
  private static final String MESSAGE = "message";
  private final JSONParser jsonParser = new JSONParser();
  private final String projectDir;
  private final Consumer<ClippyIssue> consumer;

  private ClippyJsonReportReader(String projectDir, Consumer<ClippyIssue> consumer) {
    this.projectDir = projectDir;
    this.consumer = consumer;
  }

  static void read(InputStream in, String projectDir, Consumer<ClippyIssue> consumer) throws IOException, ParseException {
    new ClippyJsonReportReader(projectDir, consumer).read(in);
  }

  private static String suggestedMessage(JSONObject obj) {
    if (obj == null)
      return null;
    JSONArray spans = (JSONArray) obj.get("spans");
    if ((spans == null) || spans.isEmpty())
      return null;
    JSONObject span = (JSONObject) spans.get(0);
    return (String) span.get("suggested_replacement");
  }

  private static Integer toInteger(Object value) {
    if (value instanceof Number) {
      return ((Number) value).intValue();
    }
    return null;
  }

  public static InputStream toJSON(File rawReport) throws IOException {

    if (rawReport == null) {
      throw new FileNotFoundException();
    }

    var sb = new StringBuilder(BEGINJSON);

    // read text report line by line
    String reportPath = rawReport.getAbsolutePath();
    BufferedReader reader;

    try (var bufferedReader = reader = new BufferedReader(new FileReader(reportPath))) {
      String line = reader.readLine();
      var separator = "";
      while (line != null) {
        // a valid Clippy result needs to be a valid json String
        if (line.startsWith("{") && line.endsWith("}")) {
          sb.append(separator).append(line);
          separator = ",";
        }
        line = reader.readLine();
      }
    }
    reader.close();
    sb.append(ENDJSON);
    return new ByteArrayInputStream(sb.toString().getBytes());

  }

  private void read(InputStream in) throws IOException, ParseException {
    JSONObject rootObject = (JSONObject) jsonParser.parse(new InputStreamReader(in, UTF_8));
    JSONArray results = (JSONArray) rootObject.get(RESULTS);
    if (results != null) {
      ((Stream<JSONObject>) results.stream()).forEach(this::onResult);
    }
  }

  private void onResult(JSONObject result) {

    var clippyIssue = new ClippyIssue();
    JSONObject message = (JSONObject) result.get(MESSAGE);

    if (message == null)
      return; // Exit silently when JSON is not compliant
    JSONObject code = (JSONObject) message.get("code");

    if (code == null)
      return; // Exit silently when JSON is not compliant

    clippyIssue.ruleKey = (String) code.get("code");
    JSONArray spans = (JSONArray) message.get("spans");

    if ((spans == null) || spans.isEmpty())
      return; // Exit silently when JSON is not compliant

    JSONObject span = (JSONObject) spans.get(0);
    clippyIssue.filePath = Paths.get(this.projectDir, (String) span.get("file_name")).toString();
    clippyIssue.message = (String) message.get(MESSAGE);
    JSONArray children = (JSONArray) message.get("children");

    if ((clippyIssue.message != null) && (children != null) && !children.isEmpty()) {
      addHelpDetails(clippyIssue, children);
    }

    clippyIssue.lineNumberStart = toInteger(span.get("line_start"));
    clippyIssue.lineNumberEnd = toInteger(span.get("line_end"));
    clippyIssue.colNumberStart = toInteger(span.get("column_start"));
    clippyIssue.colNumberEnd = toInteger(span.get("column_end"));
    clippyIssue.severity = (String) message.get("level");

    consumer.accept(clippyIssue);
  }

  private void addHelpDetails(ClippyIssue clippyIssue, JSONArray children) {
    int sz = children.size();
    var sb = new StringBuilder(clippyIssue.message);

    for (var i = 0; i < sz; i++) {
      JSONObject child = (JSONObject) children.get(i);
      String level = (String) child.get("level");
      String childMsg = (String) child.get(MESSAGE);

      // ignore some of the children
      boolean isNote = level.equalsIgnoreCase("note");
      boolean isVisitLink = childMsg.startsWith(VISIT_MSG);
      if (isNote || isVisitLink)
        continue;

      sb.append("\n").append(childMsg);

      // Are there any suggested replacement ?
      String replStr = suggestedMessage(child);
      if (replStr != null)
        sb.append("\n").append(replStr);

    }
    clippyIssue.message = sb.toString();
  }

  public static class ClippyIssue {
    @Nullable
    String filePath;
    @Nullable
    String ruleKey;
    @Nullable
    String message;
    @Nullable
    Integer lineNumberStart;
    @Nullable
    Integer lineNumberEnd;
    @Nullable
    Integer colNumberStart;
    @Nullable
    Integer colNumberEnd;
    @Nullable
    String severity;
  }

}
