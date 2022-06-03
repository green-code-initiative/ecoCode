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
package org.elegoff.plugins.communityrust.rules;

import rust.checks.CheckList;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Context;
import org.sonar.api.server.rule.RulesDefinition.Repository;

import static org.fest.assertions.Assertions.assertThat;


public class RustRulesDefinitionTest {

    private Context context = new Context();

    @Test
    public void test() {
        Repository repository = createRepository();

        assertThat(repository.name()).isEqualTo("Community RUST");
        assertThat(repository.language()).isEqualTo("rust");
        assertThat(repository.rules()).hasSize(CheckList.getRustChecks().size());

        RulesDefinition.Rule emptyEnumRule = repository.rule("EmptyEnum");
        assertThat(emptyEnumRule).isNotNull();
        assertThat(emptyEnumRule.name()).isEqualTo("Enum should not be left empty");
        assertThat(emptyEnumRule.template()).isFalse();

        assertThat(repository.rule("LineLength").template()).isFalse();

        for (RulesDefinition.Rule rule : repository.rules()) {
            for (RulesDefinition.Param param : rule.params()) {
                assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
            }
        }
    }

   
    private Repository createRepository() {
        new RustRulesDefinition().define(context);
        return context.repository("community-rust");
    }
}
