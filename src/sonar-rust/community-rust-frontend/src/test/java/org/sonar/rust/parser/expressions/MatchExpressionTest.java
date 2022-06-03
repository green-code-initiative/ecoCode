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

public class MatchExpressionTest {


    @Test
    public void tesMatchArmGuard() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MATCH_ARM_GUARD))
                .matches("if { i.set(i.get() + 1); false }")

        ;
    }


    @Test
    public void tesMatchArm() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MATCH_ARM))
                .matches("1")
                .matches("a|b")
                .matches("a|b|c")
                .matches("_")
                .matches("1 | _ if { i.set(i.get() + 1); false }")
                .matches("(a,_)")
                .matches("(a,b)")
                .matches("'c'")
                .matches("b'c'")
                .matches("Err(true_prior)")

        ;
    }


    @Test
    public void tesMatchArms() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MATCH_ARMS))
                .matches("1 => println!(\"one\")")
                .matches("_ => println!(\"anything else\")")
                .matches("1 => println!(\"one\")," +
                         "7 => println!(\"seven\")")
                .matches("1 => println!(\"one\")," +
                         "_ => println!(\"other\")")
                .matches("1 => println!(\"one\")," +
                        "2 => println!(\"two\")," +
                        "_ => println!(\"other\")")
                .matches("1 => println!(\"one\"),\n" +
                        "2 => println!(\"two\"),\n" +
                        "3 => println!(\"three\"),\n" +
                        "4 => println!(\"four\"),\n" +
                        "_ => println!(\"other\")")
                .matches("S(z @ 1, _) => assert_eq!(z, 1)")
                .matches("S(z @ 1, _) | S(_, z @ 2) => assert_eq!(z, 1)")
                .matches("_ => 42")
                .matches("_ => {},")
                 .matches("_ => {}")

                .matches("1 | _ if { i.set(i.get() + 1); false } => {}" )
                .matches("1 | _ if { i.set(i.get() + 1); false } => {}\n" +
                        "_ => {}")


                .matches("(1, 0) => dest.write_char('n')")
                .matches("(1, 0) => dest.write_char('n'),")
                .matches("semver_parser::version::Identifier::Numeric(n) => dest.write_char('n'),")
                .matches("semver_parser::version::Identifier::Numeric(n) => Identifier.Numeric('n'),")
                .matches("semver_parser::version::Identifier::Numeric(n) => Identifier::Numeric('n'),")
                .matches("semver_parser::version::Identifier::Numeric(n) => Identifier::Numeric(n),")
                .matches("(&http::Method::GET, \"/json/version\") => {\n" +
                        "              handle_json_version_request(json_version_response.clone())\n" +
                        "            }")
                .matches("(&http::Method::GET, \"/json\") => {\n" +
                        "              handle_json_request(inspector_map.clone())\n" +
                        "            }")
                .matches("'c' => ClipboardType::Clipboard")
                .matches("b'c' => ClipboardType::Clipboard")
                .matches("42 => 'b'")
                .matches("'a' => 'b'")
                .matches("Err(true_prior) => {\n" +
                        "                    prior = true_prior;\n" +
                        "                }")


                ;



    }

    @Test
    public void tesMatchExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MATCH_EXPRESSION))
                .matches("match x {\n" +
                        "    1 => println!(\"one\"),\n" +
                        "    2 => println!(\"two\"),\n" +
                        "    3 => println!(\"three\"),\n" +
                        "    4 => println!(\"four\"),\n" +
                        "    5 => println!(\"five\"),\n" +
                        "    _ => println!(\"something else\")" +
                        "}")
                .matches("match matches.value {\n" +
                        "    1 => println!(\"one\"),\n" +
                        "    _ => println!(\"something else\")" +
                        "}")
                .matches("match if_prefix {\n" +
                        "    1 => println!(\"one\"),\n" +
                        "    _ => println!(\"something else\")" +
                        "}")
                .matches("match S(1, 2) {\n" +
                        "    S(z @ 1, _) | S(_, z @ 2) => assert_eq!(z, 1),\n" +
                        "    _ => panic!()" +
                        "}")
                .matches("match foo {\n" +
                        "    1 | _ if { i.set(i.get() + 1); false } => {}\n" +
                        "    \n" +
                        "}")
                .matches("match RequestHeader::from_raw(&record_buf) {\n" +
                        "            Some(r) => r,\n" +
                        "            None => {\n" +
                        "                let error_class = b\"TypeError\";\n" +
                        "                let error_message = b\"Unparsable control buffer\";\n" +
                        "                let len = error_class.len() + error_message.len();\n" +
                        "                let padding = gen_padding_32bit(len);\n" +
                        "                let resp_header = ResponseHeader {\n" +
                        "                    request_id: 0,\n" +
                        "                    status: 1,\n" +
                        "                    result: error_class.len() as u32,\n" +
                        "                };\n" +
                        "                return Op::Sync(\n" +
                        "                    error_class\n" +
                        "                        .iter()\n" +
                        "                        .chain(error_message.iter())\n" +
                        "                        .chain(padding)\n" +
                        "                        .chain(&Into::<[u8; 16]>::into(resp_header))\n" +
                        "                        .cloned()\n" +
                        "                        .collect(),\n" +
                        "                );\n" +
                        "            }\n" +
                        "        }")
                .matches("match op_fn(&mut state.borrow_mut(), req_header.argument, &mut zero_copy) {\n" +
                        "            Ok(possibly_vector) => {\n" +
                        "               \n" +
                        "\n" +
                        "               42\n" +
                        "            }\n" +
                        "            Err(error) => {\n" +
                        "                \n" +
                        "                return 43;\n" +
                        "            }\n" +
                        "        }")
                .matches("match op_fn(&mut state.borrow_mut(), req_header.argument, &mut zero_copy) {\n" +
                        "            Ok(possibly_vector) => {\n" +
                        "               \n" +
                        "\n" +
                        "               42\n" +
                        "            }\n" +
                        "            Err(error) => {\n" +
                        "                \n" +
                        "                return 43;\n" +
                        "            }\n" +
                        "        }")
                .matches("match path.parent() {\n" +
                        "             Some(ref parent) => self.ensure_dir_exists(parent),\n" +
                        "             None => Ok(()),\n" +
                        "         }")
                .matches("match (stack.pop(), token) {\n" +
                        "              (Some(Token::LParen), Token::RParen)\n" +
                        "              | (Some(Token::LBracket), Token::RBracket)\n" +
                        "              | (Some(Token::LBrace), Token::RBrace)\n" +
                        "              | (Some(Token::DollarLBrace), Token::RBrace) => {}\n" +
                        "              (Some(left), _) => {\n" +
                        "                return Ok(ValidationResult::Invalid(Some(format!(\n" +
                        "                  \"Mismatched pairs: {:?} is not properly closed\",\n" +
                        "                  left\n" +
                        "                ))))\n" +
                        "              }\n" +
                        "              (None, _) => {\n" +
                        "                // While technically invalid when unpaired, it should be V8's task to output error instead.\n" +
                        "                // Thus marked as valid with no info.\n" +
                        "                return Ok(ValidationResult::Valid(None));\n" +
                        "              }\n" +
                        "            }")
                .matches("match f {\n" +
                        "            a  => {\n" +
                        "              handle.a()\n" +
                        "            }\n" +
                        "            b  => {\n" +
                        "              handle.b(c)\n" +
                        "            }\n" +
                        "            _ => 42\n" +
                        "          }")
                .matches("match f {\n" +
                        "            a  => {\n" +
                        "              handle.a()\n" +
                        "            }\n" +
                        "            (b,c)  => {\n" +
                        "              handle.b(c)\n" +
                        "            }\n" +
                        "            _ => 42\n" +
                        "          }")
                .matches("match new_state {\n" +
                        "      PollState::Polling => 42,\n" +
                        "            }")
                .matches("match new_state {\n" +

                        "      PollState::Polling => {} // Poll the session handler again.\n" +

                        "            }")
                .matches("match new_state {\n" +

                        "        PollState::Parked => thread::park(), // Park the thread.\n" +

                        "            }")
                .matches("match new_state {\n" +
                        "         42 => break foo\n" +
                        "            }")
                .matches("match match_suffix {\n" +
                        "         42 => break foo\n" +
                        "            }")
                .matches("match new_state {\n" +
                        "         PollState::Idle => break Ok(Poll::Pending),\n" +
                        "            }")

                .matches("match new_state {\n" +
                        "         PollState::Idle => break Ok(Poll::Pending), // Yield to task.\n" +
                        "      PollState::Polling => {} // Poll the session handler again.\n" +
                        "        PollState::Parked => thread::park(), // Park the thread.\n" +
                        "          _ => unreachable!(),\n" +
                        "            }")
                .matches("match foo {\n" +
                        "                is_ok(foo)\n" +
                        "                        if true =>\n" +
                        "                            {\n" +
                        "                                match is_really_ok(foo) {\n" +
                        "                                    val => true,\n" +
                        "                                    _ => false,\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        _ => false,\n" +
                        "                    }")



        ;
    }
}