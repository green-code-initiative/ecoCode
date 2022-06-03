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

public class MethodCallExpressionTest {

    @Test
    public void testMethodCallExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("\"Some string\".to_string()")
                .matches("\"3.14\".parse()")
                .matches("a.b()")
                .matches("a.b().c()")
                .matches("pi.unwrap_or(1.0).log(2.72)")
                .matches("j.set(i.get())")
                .matches("j.set(1)")
                .matches("Some::<i32>.calc()")
                .matches("a.foo()")
                .matches("b.abc()")
                .matches("obj.add(1i32,2i32)")
                .matches("callme().now()")
                .matches("callme()\n" +
                        ".now()")
                .matches("idf\n" +
                        ".fun()")
                .matches("d::mycall(a.clone(), b.clone()).unwrap()")
                .matches("node_fetch::create_http_client(user_agent.clone(), my_data.clone()).unwrap()")
                .matches("node_fetch::create_http_client(user_agent.clone(), my_data.clone())\n" +
                        "        .unwrap()")
                .matches("couple[0].to_lowercase()")
                .matches("self.0.iter()")
                .matches("(state.borrow().get_error_class_fn)(&error).as_bytes()")
                .matches("resp_header[0..8].foo()")
                .matches("mystruct.myfield")
                .matches("other.major")
                .matches("foo().x")
                .matches("(Struct {a: 10, b: 20}).a")
                .matches("t.get_error_class")
                .matches("state.borrow().get_error_class_fn")
                .matches("self.get_cache_filename(url)")
                .matches("(disk_byte as char).to_string()")
                .matches("async move {}.local()")
                .matches("self.state")
                .matches("async move {\n" +
                        "      let source_file = file_fetcher\n" +
                        "        .fetch(&requested_specifier, &mut permissions)\n" +
                        "        .await\n" +
                        "        .map_err(|err| {\n" +
                        "          let err = if let Some(e) = err.downcast_ref::<std::io::Error>() {\n" +
                        "            if e.kind() == std::io::ErrorKind::NotFound {\n" +
                        "              let message = if let Some(location) = &maybe_location {\n" +
                        "                format!(\n" +
                        "                  \"Cannot resolve module \\\"{}\\\" from \\\"{}\\\".\",\n" +
                        "                  requested_specifier, location.filename\n" +
                        "                )\n" +
                        "              } else {\n" +
                        "                format!(\"Cannot resolve module \\\"{}\\\".\", requested_specifier)\n" +
                        "              };\n" +
                        "              custom_error(\"NotFound\", message)\n" +
                        "            } else {\n" +
                        "              err\n" +
                        "            }\n" +
                        "          } else {\n" +
                        "            err\n" +
                        "          };\n" +
                        "          if let Some(location) = maybe_location {\n" +
                        "            // Injected modules (like test and eval) come with locations, but\n" +
                        "            // they are confusing to the user to print out the location because\n" +
                        "            // they cannot actually get to the source code that is quoted, as\n" +
                        "            // it only exists in the runtime memory of Deno.\n" +
                        "            if !location.filename.contains(\"$deno$\") {\n" +
                        "              (\n" +
                        "                requested_specifier.clone(),\n" +
                        "                HandlerError::FetchErrorWithLocation(err.to_string(), location)\n" +
                        "                  .into(),\n" +
                        "              )\n" +
                        "            } else {\n" +
                        "              (requested_specifier.clone(), err)\n" +
                        "            }\n" +
                        "          } else {\n" +
                        "            (requested_specifier.clone(), err)\n" +
                        "          }\n" +
                        "        })?;\n" +
                        "      let url = &source_file.specifier;\n" +
                        "      let is_remote = !(url.scheme() == \"file\"\n" +
                        "        || url.scheme() == \"data\"\n" +
                        "        || url.scheme() == \"blob\");\n" +
                        "      let filename = disk_cache.get_cache_filename_with_extension(url, \"meta\");\n" +
                        "      let maybe_version = if let Some(filename) = filename {\n" +
                        "        if let Ok(bytes) = disk_cache.get(&filename) {\n" +
                        "          if let Ok(compiled_file_metadata) =\n" +
                        "            CompiledFileMetadata::from_bytes(&bytes)\n" +
                        "          {\n" +
                        "            Some(compiled_file_metadata.version_hash)\n" +
                        "          } else {\n" +
                        "            None\n" +
                        "          }\n" +
                        "        } else {\n" +
                        "          None\n" +
                        "        }\n" +
                        "      } else {\n" +
                        "        None\n" +
                        "      };\n" +
                        "\n" +
                        "      let mut maybe_map_path = None;\n" +
                        "      let map_path =\n" +
                        "        disk_cache.get_cache_filename_with_extension(&url, \"js.map\");\n" +
                        "      let maybe_map = if let Some(map_path) = map_path {\n" +
                        "        if let Ok(map) = disk_cache.get(&map_path) {\n" +
                        "          maybe_map_path = Some(disk_cache.location.join(map_path));\n" +
                        "          Some(String::from_utf8(map).unwrap())\n" +
                        "        } else {\n" +
                        "          None\n" +
                        "        }\n" +
                        "      } else {\n" +
                        "        None\n" +
                        "      };\n" +
                        "      let mut maybe_emit = None;\n" +
                        "      let mut maybe_emit_path = None;\n" +
                        "      let emit_path = disk_cache.get_cache_filename_with_extension(&url, \"js\");\n" +
                        "      if let Some(emit_path) = emit_path {\n" +
                        "        if let Ok(code) = disk_cache.get(&emit_path) {\n" +
                        "          maybe_emit =\n" +
                        "            Some(Emit::Cli((String::from_utf8(code).unwrap(), maybe_map)));\n" +
                        "          maybe_emit_path =\n" +
                        "            Some((disk_cache.location.join(emit_path), maybe_map_path));\n" +
                        "        }\n" +
                        "      };\n" +
                        "\n" +
                        "      Ok(CachedModule {\n" +
                        "        is_remote,\n" +
                        "        maybe_dependencies: None,\n" +
                        "        maybe_emit,\n" +
                        "        maybe_emit_path,\n" +
                        "        maybe_types: source_file.maybe_types,\n" +
                        "        maybe_version,\n" +
                        "        media_type: source_file.media_type,\n" +
                        "        requested_specifier,\n" +
                        "        source: source_file.source,\n" +
                        "        source_path: source_file.local,\n" +
                        "        specifier: source_file.specifier,\n" +
                        "      })\n" +
                        "    }\n" +
                        "    .boxed()")
                .matches("runtime.execute(\"foo\", r#\"raw\"#)")
                .matches("keys.drain(..)")
                .matches("a321.into()")
                .matches("321.await")
                .matches("321.into()")
                .matches("crate_root.call(me)")


        ;

    }


}
