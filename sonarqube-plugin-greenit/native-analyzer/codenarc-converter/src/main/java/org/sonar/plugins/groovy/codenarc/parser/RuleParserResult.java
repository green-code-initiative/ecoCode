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
package org.sonar.plugins.groovy.codenarc.parser;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.sonar.plugins.groovy.codenarc.Rule;
import org.sonar.plugins.groovy.codenarc.RuleSet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RuleParserResult {
    private final String version;
    private final Multimap<RuleSet, Rule> result = LinkedListMultimap.create();

    public RuleParserResult(String codenarcVersion) {
        this.version = codenarcVersion;
    }

    public void addRule(RuleSet ruleSet, Rule rule) {
        result.put(ruleSet, rule);
    }

    public String getCodeNarcVersion() {
        return version;
    }

    public Collection<Rule> getRulesBy(RuleSet ruleSet) {
        return Collections.unmodifiableCollection(result.get(ruleSet));
    }

    public long getCount() {
        return result.asMap().values().stream().mapToLong(Collection::size).sum();
    }

    public Map<String, Long> getRuleCountByVersion() {
        return result.asMap().values().stream().flatMap(Collection::stream)
                .sorted(Comparator.comparing(r -> withDefault(r.version)))
                .collect(Collectors.groupingBy(r -> withDefault(r.version), LinkedHashMap::new, Collectors.counting()));
    }

    public Map<String, Long> getRuleCountByCategory() {
        return result.asMap().values().stream().flatMap(Collection::stream)
                .flatMap(r -> r.tags.stream())
                .sorted(Comparator.comparing(Function.identity()))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
    }

    private String withDefault(String version) {
        return Optional.ofNullable(version).orElse("legacy");
    }
}
