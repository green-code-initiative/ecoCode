/*
 * SonarQube Java
 * Copyright (C) 2012-2021 SonarSource SA
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
package fr.cnumr.python;

import fr.cnumr.python.checks.*;
import org.sonar.plugins.python.api.PythonCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RulesList {

    private RulesList() {
    }

    public static List<Class<? extends PythonCheck>> getChecks() {
        List<Class<? extends PythonCheck>> checks = new ArrayList<>();
        checks.addAll(getPythonChecks());
        checks.addAll(getPythonTestChecks());
        return Collections.unmodifiableList(checks);
    }

    public static List<Class<? extends PythonCheck>> getPythonChecks() {
        return Collections.unmodifiableList(Arrays.asList(
                AvoidGlobalVariableInFunctionCheck.class,
                AvoidFullSQLRequest.class,
                AvoidSQLRequestInLoop.class,
                AvoidTryCatchFinallyCheck.class,
                NoFunctionCallWhenDeclaringForLoop.class,
                AvoidGettersAndSetters.class
        ));
    }

    public static List<Class<? extends PythonCheck>> getPythonTestChecks() {
        return Collections.emptyList();
    }
}
