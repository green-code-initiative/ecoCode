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
package org.sonar.plugins.groovy.codenarc;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.codehaus.staxmate.in.SMInputCursor;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.plugins.groovy.utils.StaxParser;

public final class CodeNarcXMLParser implements StaxParser.XmlStreamHandler {

  private final List<CodeNarcViolation> result = new ArrayList<>();
  private final FileSystem fileSystem;

  private CodeNarcXMLParser(final FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  public static List<CodeNarcViolation> parse(File file, FileSystem fileSystem) {
    CodeNarcXMLParser handler = new CodeNarcXMLParser(fileSystem);
    try {
      new StaxParser(handler).parse(file);
    } catch (XMLStreamException e) {
      throw new IllegalStateException("Unabel to parse file: " + file, e);
    }
    return handler.result;
  }

  @Override
  public void stream(SMHierarchicCursor rootCursor) throws XMLStreamException {
    rootCursor.advance();
    SMInputCursor items = rootCursor.descendantElementCursor();
    List<String> sourceDirectories = new LinkedList<>();
    while (items.getNext() != null) {
      String localName = items.getLocalName();
      if ("Project".equals(localName)) {
        extractSourceDirectories(items, sourceDirectories);
      } else if ("Package".equals(localName)) {
        extractIssues(items, sourceDirectories);
      }
    }
  }

  private void extractIssues(SMInputCursor items, List<String> sourceDirectories) throws XMLStreamException {
    String packPath = items.getAttrValue("path");
    SMInputCursor file = items.descendantElementCursor("File");
    while (file.getNext() != null) {
      String filename = getFilename(sourceDirectories, packPath, file.getAttrValue("name"));
      SMInputCursor violation = file.childElementCursor("Violation");
      while (violation.getNext() != null) {
        String lineNumber = violation.getAttrValue("lineNumber");
        String ruleName = violation.getAttrValue("ruleName");

        SMInputCursor messageCursor = violation.childElementCursor("Message");
        String message = messageCursor.getNext() == null ? "" : messageCursor.collectDescendantText(true);

        result.add(new CodeNarcViolation(ruleName, filename, lineNumber, message));
      }
    }
  }

  private static void extractSourceDirectories(SMInputCursor items, List<String> sourceDirectories) throws XMLStreamException {
    SMInputCursor sourceDirectoryCursor = items.descendantElementCursor("SourceDirectory");
    while (sourceDirectoryCursor.getNext() != null) {
      String value = sourceDirectoryCursor.getElemStringValue();
      if (StringUtils.isNotBlank(value)) {
        sourceDirectories.add(value.trim().replaceAll("\\\\", "/") + "/");
      }
    }
  }

  private String getFilename(List<String> sourceDirectories, String packPath, String attrFilename) throws XMLStreamException {
    FilePredicates pred = fileSystem.predicates();
    for (String directory : sourceDirectories) {
      String path = directory + packPath + "/" + attrFilename;
      if (fileSystem.hasFiles(pred.and(pred.hasType(Type.MAIN), pred.hasAbsolutePath(path)))) {
        return path;
      }
    }
    return packPath + "/" + attrFilename;
  }

  public static class CodeNarcViolation {
    private final String ruleName;
    private final String filename;
    private final Integer line;
    private final String message;

    public CodeNarcViolation(String ruleName, String filename, String lineNumber, String message) {
      this.ruleName = ruleName;
      this.filename = filename;
      this.line = StringUtils.isBlank(lineNumber) ? null : Integer.parseInt(lineNumber);
      this.message = message;
    }

    public String getRuleName() {
      return ruleName;
    }

    public String getFilename() {
      return filename;
    }

    public Integer getLine() {
      return line;
    }

    public String getMessage() {
      return message;
    }

  }

}
