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
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class ClippyRulesDefinitionTest {

  @Test
  public void clippyExternalRepository() {
    RulesDefinition.Context context = new RulesDefinition.Context();
    ClippyRulesDefinition rulesDefinition = new ClippyRulesDefinition();
    rulesDefinition.define(context);

    assertThat(context.repositories()).hasSize(1);
    RulesDefinition.Repository repository = context.repository("external_clippy");
    assertThat(repository).isNotNull();
    assertThat(repository.name()).isEqualTo("Clippy");
    assertThat(repository.language()).isEqualTo("rust");
    assertThat(repository.isExternal()).isTrue();
    assertThat(repository.rules().size()).isEqualTo(518);

    RulesDefinition.Rule rule = repository.rule("clippy::absurd_extreme_comparisons");
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo("Checks for comparisons where one side of the relation is either the minimum or maximum value for its");
    assertThat(rule.htmlDescription()).isEqualTo("See description of Clippy rule <code>clippy::absurd_extreme_comparisons</code> at" +
      " the <a href=\"https://rust-lang.github.io/rust-clippy/master/index.html#absurd_extreme_comparisons\">Clippy website</a>.");
    assertThat(rule.tags()).isEmpty();
  }
}
