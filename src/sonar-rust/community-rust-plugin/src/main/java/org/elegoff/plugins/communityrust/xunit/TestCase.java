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
package org.elegoff.plugins.communityrust.xunit;

import javax.annotation.Nullable;

/**
 * Represents a unit test case. Has a couple of data items like name, status, time etc. associated. Reports testcase details in
 * sonar-conform XML
 */
public class TestCase {
  private final String name;
  private final TestCaseStatus status;
  private final String stackTrace;
  private final String errorMessage;
  private final int time;
  private final String file;
  private final String testClassname;

  /**
   * Constructs a testcase instance out of following parameters
   * @param status The execution status of the testcase
   * @param time The execution time in milliseconds
   * @param file The optional file to which this test case applies.
   */
  public TestCase(String name, TestCaseStatus status, String stackTrace, String errorMessage, int time, @Nullable String file, @Nullable String testClassname) {
    this.name = name;
    this.status = status;
    this.stackTrace = stackTrace;
    this.errorMessage = errorMessage;
    this.time = time;
    this.file = file;
    this.testClassname = testClassname;
  }

  /**
   * Returns true if this testcase is an error, false otherwise
   */
  public boolean isError() {
    return TestCaseStatus.ERROR.equals(status);
  }

  /**
   * Returns true if this testcase is a failure, false otherwise
   */
  public boolean isFailure() {
    return TestCaseStatus.FAILURE.equals(status);
  }

  /**
   * Returns true if this testcase has been skipped, failure, false otherwise
   */
  public boolean isSkipped() {
    return TestCaseStatus.SKIPPED.equals(status);
  }

  public int getTime() {
    return time;
  }

  public String getFile() {
    return file;
  }

  public String getName() {
    return name;
  }

  public TestCaseStatus getStatus() {
    return status;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getTestClassname() {
    return testClassname;
  }
}
