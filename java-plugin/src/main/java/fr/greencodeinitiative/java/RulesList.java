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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.greencodeinitiative.java.checks.*;
import org.sonar.plugins.java.api.JavaCheck;

public final class RulesList {

    private RulesList() {
    }

    public static List<Class<? extends JavaCheck>> getChecks() {
        List<Class<? extends JavaCheck>> checks = new ArrayList<>();
        checks.addAll(getJavaChecks());
        checks.addAll(getJavaTestChecks());
        return Collections.unmodifiableList(checks);
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return Collections.unmodifiableList(Arrays.asList(
                ArrayCopyCheck.class,
                IncrementCheck.class,
                AvoidConcatenateStringsInLoop.class,
                AvoidUsageOfStaticCollections.class,
                AvoidGettingSizeCollectionInLoop.class,
                AvoidRegexPatternNotStatic.class,
                NoFunctionCallWhenDeclaringForLoop.class,
                AvoidStatementForDMLQueries.class,
                AvoidSpringRepositoryCallInLoopCheck.class,
                AvoidSpringRepositoryCallInLoopWithStream.class,
                AvoidSQLRequestInLoop.class,
                AvoidFullSQLRequest.class,
                UseCorrectForLoop.class,
                UnnecessarilyAssignValuesToVariables.class,
                OptimizeReadFileExceptions.class,
                InitializeBufferWithAppropriateSize.class,
                AvoidUsingGlobalVariablesCheck.class,
                AvoidSetConstantInBatchUpdate.class,
                FreeResourcesOfAutoCloseableInterface.class,
                AvoidMultipleIfElseStatement.class
        ));
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return Collections.emptyList();
    }
}
