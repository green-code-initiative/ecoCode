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
package io.ecocode.java.checks.idleness;

import io.ecocode.java.checks.helpers.constant.FlagOnMethodCheck;
import org.sonar.check.Rule;

/**
 * Check the call of "FLAG_KEEP_SCREEN_ON" on the method "setFlags" (both parameters) of "android.view.Window".
 */
@Rule(key = "EIDL002", name = "ecoCodeKeepScreenOnSetFlagRule")
public class KeepScreenOnSetFlagsRule extends FlagOnMethodCheck {

    public KeepScreenOnSetFlagsRule() {
        super("setFlags", "android.view.Window", 0x00000080, 0, 1);
    }

    @Override
    public String getMessage() {
        return "Keeping the screen on should be avoided to avoid draining the battery.";
    }
}
