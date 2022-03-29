/*
 * Sonar CodeNarc Converter
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

import org.codenarc.rule.AbstractRule;

public enum RuleSet {
  ECOCODE("ecocode");
//  BASIC("basic"),
//  BRACES("braces"),
//  COMMENTS("comments"), // new in 1.3
//  CONCURRENCY("concurrency"),
//  CONVENTION("convention"), // new in 0.16
//  DESIGN("design"),
//  DRY("dry"),
//  ENHANCED("enhanced"), // new in 1.1
//  EXCEPTIONS("exceptions"),
//  FORMATTING("formatting"), // new in 0.15
//  GENERIC("generic"),
//  GRAILS("grails"),
//  GROOVYISM("groovyism"), // new in 0.16
//  IMPORTS("imports"),
//  JDBC("jdbc"), // new in 0.14
//  JUNIT("junit"),
//  LOGGING("logging"),
//  NAMING("naming"),
//  SECURITY("security"), // new in 0.14
//  SERIALIZATION("serialization"), // new in 0.14
//  SIZE("size"),
//  UNNECESSARY("unnecessary"),
//  UNUSED("unused");


  private final String label;

  RuleSet(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static RuleSet getCategory(Class<? extends AbstractRule> ruleClass) {
    String[] name = ruleClass.getCanonicalName().split("\\.");
    return RuleSet.valueOf(name[3].toUpperCase());
  }
}
