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
package io.ecocode.java.checks.helpers.constant;

public class MethodSpecs {
    private final String methodOwner;
    private final String methodName;
    private final Object constantValueToCheck;
    private final int[] paramPositions;

    public MethodSpecs(String methName, String methOwner, Object constant, int... optionPos) {
        this.methodOwner = methOwner;
        this.methodName = methName;
        this.constantValueToCheck = constant;
        this.paramPositions = optionPos;
    }

    public MethodSpecs(String methName, String methOwner, Object constant) {
        this.methodOwner = methOwner;
        this.methodName = methName;
        this.constantValueToCheck = constant;
        this.paramPositions = new int[]{0};
    }

    public String getMethodOwner() {
        return methodOwner;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getConstantValueToCheck() {
        return constantValueToCheck;
    }

    public int[] getParamsPositions() {
        return paramPositions;
    }
}
