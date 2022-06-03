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
import org.sonar.check.RuleProperty;
import org.sonar.rust.RustGrammar;

@Rule(key = "FunctionParametersCount")
public class FunctionParametersCountCheck extends RustCheck {

  private static final int DEFAULT_MAXIMUM_PARAMETER_COUNT = 8;

  @RuleProperty(
    key = "maximumParameterCount",
    description = " Maximum authorized number of parameters",
    defaultValue = "" + DEFAULT_MAXIMUM_PARAMETER_COUNT)
  public int maximumParameterCount = DEFAULT_MAXIMUM_PARAMETER_COUNT;

  private static int getNumberOfParameters(AstNode node) {
    AstNode parameterNameList = node.getFirstChild(RustGrammar.FUNCTION_PARAMETERS);

    return parameterNameList == null ? 0 : parameterNameList.getChildren(RustGrammar.FUNCTION_PARAM).size();
  }

  @Override
  public Set<AstNodeType> subscribedKinds() {
    return Collections.singleton(RustGrammar.FUNCTION);
  }

  @Override
  public void visitNode(AstNode node) {
    int numberOfParameters = getNumberOfParameters(node);

    if (numberOfParameters > maximumParameterCount) {
      addIssue(
        "Reduce the number of parameters that this function takes from " + numberOfParameters + " to at most " + maximumParameterCount + ".",
        node);
    }
  }

}
