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
package fr.greencodeinitiative.php;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.greencodeinitiative.php.checks.AvoidDoubleQuoteCheck;
import fr.greencodeinitiative.php.checks.AvoidFullSQLRequestCheck;
import fr.greencodeinitiative.php.checks.AvoidSQLRequestInLoopCheck;
import fr.greencodeinitiative.php.checks.AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements;
import fr.greencodeinitiative.php.checks.IncrementCheck;
import fr.greencodeinitiative.php.checks.NoFunctionCallWhenDeclaringForLoop;

public final class RulesList {

    private RulesList() {
    }

    public static List<Class<?>> getChecks() {
        List<Class<?>> checks = new ArrayList<>();
        checks.addAll(getPhpChecks());
        checks.addAll(getPhpTestChecks());
        return Collections.unmodifiableList(checks);
    }

    public static List<Class<?>> getPhpChecks() {
        return Collections.unmodifiableList(Arrays.asList(
                IncrementCheck.class,
                AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.class,
                AvoidDoubleQuoteCheck.class,
                AvoidFullSQLRequestCheck.class,
                AvoidSQLRequestInLoopCheck.class,
                NoFunctionCallWhenDeclaringForLoop.class
        ));
    }

    public static List<Class<?>> getPhpTestChecks() {
        return Collections.emptyList();
    }
}
