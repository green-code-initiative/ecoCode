/*
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
const path = require("path");
const autoprefixer = require("autoprefixer");

module.exports = {
  // Define the entry points here. They MUST have the same name as the page_id
  // defined in src/main/java/org/sonarsource/plugins/example/web/MyPluginPageDefinition.java
  entry: {
    // Using Vanilla JS:
    global_page: ["./src/main/js/global_page/index.js"],

    // Using Backbone JS:
    project_page: ["./src/main/js/project_page/index.js"],

    // Using React:
    portfolio_page: ["./src/main/js/portfolio_page/index.js"],
    admin_page: ["./src/main/js/admin_page/index.js"]
  },
  output: {
    // The entry point files MUST be shipped inside the final JAR's static/
    // directory.
    path: path.join(__dirname, "../../target/classes/static"),
    filename: "[name].js"
  },
  resolve: {
    root: path.join(__dirname, "src/main/js")
  },
  externals: {
    // React 16.8 ships with SonarQube, and should be re-used to avoid 
    // collisions at runtime.
    react: "React",
    "react-dom": "ReactDOM",
    // Register the Sonar* globals as packages, to simplify importing.
    // See src/main/js/common/api.js for more information on what is exposed 
    // in SonarRequest.
    "sonar-request": "SonarRequest",
  },
  module: {
    // Our example uses Babel to transpile our code.
    loaders: [
      {
        test: /\.js$/,
        loader: "babel",
        exclude: /(node_modules)/
      },
      {
        test: /\.css/,
        loader: "style-loader!css-loader!postcss-loader"
      },
      { test: /\.json$/, loader: "json" }
    ]
  },
  postcss() {
    return [
      autoprefixer({
        browsers: [
          "last 3 Chrome versions",
          "last 3 Firefox versions",
          "last 3 Safari versions",
          "last 3 Edge versions",
          "IE 11"
        ]
      })
    ];
  }
};
