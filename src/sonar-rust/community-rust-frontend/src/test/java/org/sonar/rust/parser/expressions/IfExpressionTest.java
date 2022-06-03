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
package org.sonar.rust.parser.expressions;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class IfExpressionTest {

    @Test
    public void tesIfExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.IF_EXPRESSION))
                .matches("if x == 4 {\n" +
                        "    println!(\"x is four\");\n" +
                        "} else if x == 3 {\n" +
                        "    println!(\"x is three\");\n" +
                        "} else {\n" +
                        "    println!(\"x is something else\");\n" +
                        "}")
                .matches("if run_coverage {\n" +
                        "        println!(\"Coverage is running\");" +
                        " } ")
                .matches("if is_ok {} ")
                .matches("if if_ok {} ")
                //TODO .matches("if match_ok {} ")
                .matches("if async_ok {} ")
                .matches("if is_red || is_black {let cpt = 1 ;} else  {let cpt = 0 ;}")
                .matches("if is_red || is_black {}")
                .matches("if is_red || is_black {} else  {let cpt = 0 ;}")
                .matches("if is_ok {\n" +
                        "        // empty block\n" +
                        " } ")
                .matches("if is_ok {} else {let x = 42;}")
                .matches("if is_ok {\n" +
                        "        // empty block\n" +
                        " } else {let x = 42;}")
                .matches("if bytes.len() < 3 * 4 {\n" +
                        "        println!(\"Too short\");" +
                        "        }")
                .matches("if bytes.len() < 3 * 4 {\n" +
                        "            return None;\n" +
                        "        }")
                .matches("if cfg!(target_os = \"windows\") {\n" +
                        "            PathBuf::from(\"/first_dir/\")\n" +
                        "        } else {\n" +
                        "            PathBuf::from(\"/other_dir/\")\n" +
                        "        }")
                .matches("if cfg!(target_os = \"windows\") {\n" +
                        "                    if let Some(Component::Prefix(prefix_component)) =\n" +
                        "                    path_components.next()\n" +
                        "                    {\n" +
                        "                        match prefix_component.kind() {\n" +
                        "                            Prefix::Disk(disk_byte) | Prefix::VerbatimDisk(disk_byte) => {\n" +
                        "                                let disk = 43;\n" +
                        "                            }\n" +
                        "                            Prefix::UNC(server, share)\n" +
                        "                            | Prefix::VerbatimUNC(server, share) => {\n" +
                        "\n" +
                        "                                let host = 44;\n" +
                        "\n" +
                        "                            }\n" +
                        "                            _ => unreachable!(),\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                }")
                .matches("if is_ok \n" +
                        "            {} else {\n" +
                        "                let msg = \"Module evaluation is still pending but there are no pending ops or dynamic imports. This situation is often caused by unresolved promise.\";\n" +
                        "                return Poll::Ready(Err(generic_error(msg)));\n" +
                        "            }")
                .matches("if is_ops || has_pending_dyn_imports || has_pending_dyn_module_evaluation\n" +
                        "            {} else {\n" +
                        "                let msg = \"Module evaluation is still pending but there are no pending ops or dynamic imports. This situation is often caused by unresolved promise.\";\n" +
                        "                return Poll::Ready(Err(generic_error(msg)));\n" +
                        "            }")
                .matches("if has_ops\n" +
                        "                || has_pending_dyn_imports\n" +
                        "                || has_pending_dyn_module_evaluation\n" +
                        "            {} else {\n" +
                        "                let msg = \"Module evaluation is still pending but there are no pending ops or dynamic imports. This situation is often caused by unresolved promise.\";\n" +
                        "                return Poll::Ready(Err(generic_error(msg)));\n" +
                        "            }")
                .matches("if has_pending_ops\n" +
                        "                || has_pending_dyn_imports\n" +
                        "                || has_pending_dyn_module_evaluation\n" +
                        "            {\n" +
                        "                // pass, will be polled again\n" +
                        "            } else {\n" +
                        "                let msg = \"Module evaluation is still pending but there are no pending ops or dynamic imports. This situation is often caused by unresolved promise.\";\n" +
                        "                return Poll::Ready(Err(generic_error(msg)));\n" +
                        "            }")
                .matches("if a && b { None }")
                .matches("if !c { None }")
                .matches("if a && !b { None }")
                .matches("if state.get_state() == MyState::KO {\n" +
                        "                continue 'outer;\n" +
                        "            }")
                .matches("if match foo {\n" +
                        "                is_ok(foo)\n" +
                        "                        if true =>\n" +
                        "                            {\n" +
                        "                                match is_really_ok(foo) {\n" +
                        "                                    val => true,\n" +
                        "                                    _ => false,\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        _ => false,\n" +
                        "                    } {}")
                .matches("if next.iter().all(|i| i == 42) {\n" +
                        "                                                  \n" +
                        "                        break;\n" +
                        "                     }")
                 .matches("if next.iter().all(|&i| i == 42) {\n" +
                        "                                                  \n" +
                        "                        break;\n" +
                        "                     }")

                .matches("if *bar == None {\n" +
                        "        true\n" +
                        "    } else {\n" +
                        "        LOG_WARNING!(\"condition is false\");\n" +
                        "        false\n" +
                        "    }")
                


        ;
    }

    @Test
    public void tesIfLetExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.IF_LET_EXPRESSION))
                .matches("if let (\"Bacon\",b) = dish {\n" +
                        "    println!(\"Bacon is served with {}\", b);\n" +
                        "} else {\n" +
                        "    // This block is evaluated instead.\n" +
                        "    println!(\"No bacon will be served\");\n" +
                        "}")


        ;
    }
}