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
package org.elegoff.plugins.communityrust;

import org.assertj.core.util.Files;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;


import javax.annotation.CheckForNull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;




public class Utils {

    private Utils() {
        // utility class
    }

    public static File loadResource(String resourceName) {
        URL resource = Utils.class.getResource(resourceName);
        File resourceAsFile = null;
        try {
            resourceAsFile = new File(resource.toURI());
        } catch (URISyntaxException e) {
            System.out.println("Cannot load resource: " + resourceName);
        }

        return resourceAsFile;
    }

    /**
     * Search for a test resource in the classpath. For example getResource("org/sonar/MyClass/foo.txt");
     *
     * @param path the starting slash is optional
     * @return the resource. Null if resource not found
     */
    @CheckForNull
    public static File getResource(String path) {
        String resourcePath = path;
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }
        URL url = Utils.class.getResource(resourcePath);
        if (url != null) {
            try {
                return new File(url.toURI());
            } catch (URISyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public static DefaultInputFile buildInputFile(File baseDir, String fileName) throws IOException {
        File target = new File(baseDir, fileName);
        String content = Files.contentOf(target, StandardCharsets.UTF_8);
        DefaultInputFile inputFile = TestInputFileBuilder.create("ProjectKey", baseDir, target)
                .setContents(content)
                .setCharset(StandardCharsets.UTF_8)
                .setLanguage("Rust")
                .setType(InputFile.Type.MAIN).build();
        return inputFile;
    }

    public static String fileContent(File file, Charset charset) {
        try {
            return new String(java.nio.file.Files.readAllBytes(file.toPath()), charset);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + file, e);
        }
    }


}
