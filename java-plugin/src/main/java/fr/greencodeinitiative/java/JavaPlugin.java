/*
 * Copyright (C) 2023 Green Code Initiative
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
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative.java;

import org.sonar.api.Plugin;

public class JavaPlugin implements Plugin {

    @Override
    public void define(Context context) {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(JavaRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(JavaCheckRegistrar.class);

    }

}
