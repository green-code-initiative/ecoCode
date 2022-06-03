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

import org.junit.Test;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONArray;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONObject;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.JSONParser;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fest.assertions.Assertions.assertThat;

public class ClippyJsonReportReaderTest {
    private final JSONParser jsonParser = new JSONParser();

    @Test
    public void noReportProvided(){
        assertThatThrownBy(() -> {
            InputStream in = ClippyJsonReportReader.toJSON(null);
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void invalidReportPathProvided(){
        assertThatThrownBy(() -> {
            InputStream in = ClippyJsonReportReader.toJSON(new File("invalid.txt"));
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void emptyReport() {
        File empty = this.getFileFromResources("clippy/empty-report.txt");
        InputStream in = null;
        try {
            in = ClippyJsonReportReader.toJSON(empty);
            assertThat(in).isNotNull();
            assertThat(isValidJsonReport(in)).isTrue();
            in = ClippyJsonReportReader.toJSON(empty);
            assertThat(issueCount(in)).isZero();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void validReport() {
        File report = this.getFileFromResources("clippy/myreport.txt");
        InputStream in = null;
        try {
            in = ClippyJsonReportReader.toJSON(report);
            assertThat(in).isNotNull();
            assertThat(isValidJsonReport(in)).isTrue();
            in = ClippyJsonReportReader.toJSON(report);
            assertThat(issueCount(in)).isEqualTo(2);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    private boolean isValidJsonReport(InputStream in){
        JSONObject rootObject = null;
        boolean res = false;
        try {
            rootObject = (JSONObject) jsonParser.parse(new InputStreamReader(in, UTF_8));

            JSONArray results = (JSONArray) rootObject.get("results");
            if (results != null) return true;

        } catch (ParseException |IOException e) {
            throw new IllegalStateException(e);
        }
        return res;
    }

    private int issueCount(InputStream in){
        JSONObject rootObject = null;
        int nbIssue = 0;
        try {
            rootObject = (JSONObject) jsonParser.parse(new InputStreamReader(in, UTF_8));

            JSONArray results = (JSONArray) rootObject.get("results");
            for (Object o: results){
                JSONObject jo = (JSONObject)o;
                JSONObject message = (JSONObject)jo.get("message");
                JSONArray spans = (JSONArray)message.get("spans");
                if ((spans != null)&& spans.size()>0){
                    nbIssue++;
                }

            }
        } catch (ParseException |IOException e) {
            throw new IllegalStateException(e);
        }
        return nbIssue;
    }

}
