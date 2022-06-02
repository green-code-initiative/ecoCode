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
package org.sonar.plugins.groovy.codenarc.parser.markdown;

import org.sonar.plugins.groovy.codenarc.ParseResult;
import org.sonar.plugins.groovy.codenarc.RuleParameter;

import java.util.*;

public class RuleDescription implements ParseResult {
    private String name;
    private final StringBuilder description = new StringBuilder();
    private String since;
    private final Set<RuleParameter> parameters = new HashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean hasParameters() {
        return !parameters.isEmpty();
    }

    @Override
    public Set<RuleParameter> getParameters() {
        return Collections.unmodifiableSet(parameters);
    }

    public String getDescription() {
        return description.toString();
    }

    public void appendDescription(String description) {
        this.description.append(description);
    }

    public void addParameter(String key, String description, String defaultValue) {
        parameters.add(RuleParameter.create(key, description,defaultValue));
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
