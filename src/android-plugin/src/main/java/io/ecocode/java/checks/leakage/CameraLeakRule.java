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
package io.ecocode.java.checks.leakage;

import io.ecocode.java.checks.helpers.OpeningClosingMethodCheck;
import org.sonar.check.Rule;

/**
 * Check that Camera#release() method is closed after Camera#open().
 *
 * @see OpeningClosingMethodCheck
 */
@Rule(key = "ELEA002", name = "ecoCodeCameraLeakRule")
public class CameraLeakRule extends OpeningClosingMethodCheck {

    public CameraLeakRule() {
        super("open", "release", "android.hardware.Camera");
    }

    @Override
    public String getMessage() {
        return "Failing to call android.hardware.Camera#release() can drain the battery in just a few hours.";
    }
}
