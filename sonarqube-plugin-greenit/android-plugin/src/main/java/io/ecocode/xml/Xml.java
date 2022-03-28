/*
 * ecoCode SonarQube Plugin
 * Copyright (C) 2020-2021 Snapp' - Université de Pau et des Pays de l'Adour
 * mailto: contact@ecocode.io
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
package io.ecocode.xml;

public interface Xml { //NOSONAR - we use the interface for constant without inheriting it
    String REPOSITORY_NAME = "ecoCode";
    String PROFILE_NAME = "ecoCode";
    String KEY = "xml";
    String REPOSITORY_KEY = "ecoCode-xml";
    String XML_RESOURCE_PATH = "io/ecocode/xml/rules";
    String PROFILE_PATH = "io/ecocode/xml/rules/ecocode_xml_profile.json";
}
