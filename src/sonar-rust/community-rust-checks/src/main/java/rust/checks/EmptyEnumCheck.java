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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import java.util.Collections;
import java.util.Set;
import org.sonar.check.Rule;
import org.sonar.rust.RustGrammar;

@Rule(key = "EmptyEnum")
public class EmptyEnumCheck extends RustCheck {
  @Override
  public Set<AstNodeType> subscribedKinds() {
    return Collections.singleton(RustGrammar.ENUMERATION);
  }

  @Override
  public void visitNode(AstNode node) {
    AstNode enumItems = node.getFirstChild(RustGrammar.ENUM_ITEMS);

    if (enumItems == null) {
      raiseIssue(node);
    }
  }

  private void raiseIssue(AstNode node) {
    addIssue("Either remove or fill this empty enumeration.", node);
  }

}
