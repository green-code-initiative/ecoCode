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
package rust.checks;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.impl.Parser;
import org.sonar.rust.RustFile;
import org.sonar.rust.RustParser;
import org.sonar.rust.RustParserConfiguration;
import org.sonar.rust.RustVisitorContext;
import org.sonarsource.analyzer.commons.checks.verifier.SingleFileVerifier;

public class RustCheckVerifier {
    public static void verify(File file, RustCheck check) {
        createVerifier(file, check, true, Integer.MAX_VALUE).assertOneOrMoreIssues();
    }

    public static void verifyWithMarginRight(File file, RustCheck check, int marginRight) {
        createVerifier(file, check, true, marginRight).assertOneOrMoreIssues();
    }

    public static void verifyNoIssue(File file, RustCheck check) {
        createVerifier(file, check, true, Integer.MAX_VALUE).assertNoIssues();
    }

    public static void verifyNoIssueIgnoringExpected(File file, RustCheck check) {
        createVerifier(file, check, false, Integer.MAX_VALUE).assertNoIssues();
    }


    private static SingleFileVerifier createVerifier(File file, RustCheck check, boolean addCommentsAsExpectedIssues, int marginRight) {
        SingleFileVerifier verifier = SingleFileVerifier.create(file.toPath(), StandardCharsets.UTF_8);

        RustParserConfiguration conf = RustParserConfiguration.builder()
                .setCharset(StandardCharsets.UTF_8)
                .build();

        Parser<Grammar> parser = RustParser.create(conf);
        TestRustFile RustFile = new TestRustFile(file, StandardCharsets.UTF_8);
        RustVisitorContext context;
        try {
            context = new RustVisitorContext(RustFile, parser.parse(file));
        } catch (RecognitionException e) {
            context = new RustVisitorContext(RustFile, e);
        }

        for (Issue issue : check.scanFileForIssues(context)) {
            SingleFileVerifier.IssueBuilder issueBuilder = verifier.reportIssue(issue.message());
            Integer line = issue.line();
            if (line != null) {
                issueBuilder.onLine(line);
            } else {
                issueBuilder.onFile();
            }
        }

        if (addCommentsAsExpectedIssues) {
            parseCommentsAsExpectedIssues(verifier, RustFile);
        }

        return verifier;
    }

    private static void parseCommentsAsExpectedIssues(SingleFileVerifier verifier, TestRustFile RustFile) {
        Pattern commentPattern = Pattern.compile("/\\*.*?\\*/");
        String lines[] = RustFile.content().split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Matcher matcher = commentPattern.matcher(line);
            while (matcher.find()) {
                String commentString = matcher.group();
                verifier.addComment(i + 1, matcher.start() + 1, commentString, 2, 2);
            }
        }
    }

    private static class TestRustFile implements RustFile {

        private final File file;
        private final Charset charset;

        public TestRustFile(File file, Charset charset) {
            this.file = file;
            this.charset = charset;
        }

        @Override
        public String name() {
            return file.getName();
        }

        @Override
        public String content() {
            try {
                return new String(Files.readAllBytes(file.toPath()), charset);
            } catch (IOException e) {
                throw new IllegalStateException("Could not read " + file, e);
            }
        }

        @Override
        public URI uri() {
            return file.toURI();
        }

    }
}

