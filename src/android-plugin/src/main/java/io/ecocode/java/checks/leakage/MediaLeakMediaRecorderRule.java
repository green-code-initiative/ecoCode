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

import io.ecocode.java.checks.helpers.ConstructorBeforeMethodCheck;
import org.sonar.check.Rule;

/**
 * Check if MediaRecorder's constructor is called, the release method is called afterwards.
 *
 * @see ConstructorBeforeMethodCheck
 */
@Rule(key = "ELEA005", name = "ecocodeMediaLeakMediaRecorderRule")
public class MediaLeakMediaRecorderRule extends ConstructorBeforeMethodCheck {
    private static final String ERROR_MESSAGE = "Failing to call release() on a Media Recorder may lead to continuous battery consumption.";

    public MediaLeakMediaRecorderRule() {
        super("android.media.MediaRecorder", "release");
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
