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

import java.util.Collections;
import java.util.List;

import fr.greencodeinitiative.java.checks.*;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

/**
 * Provide the "checks" (implementations of rules) classes that are going be executed during
 * source code analysis.
 * <p>
 * This class is a batch extension by implementing the {@link org.sonar.plugins.java.api.CheckRegistrar} interface.
 */
@SonarLintSide
public class JavaCheckRegistrar implements CheckRegistrar {
    private static final List<Class<? extends JavaCheck>> ANNOTATED_RULE_CLASSES = List.of(
            ArrayCopyCheck.class,
            IncrementCheck.class,
            AvoidConcatenateStringsInLoop.class,
            AvoidUsageOfStaticCollections.class,
            AvoidGettingSizeCollectionInLoop.class,
            AvoidRegexPatternNotStatic.class,
            NoFunctionCallWhenDeclaringForLoop.class,
            AvoidStatementForDMLQueries.class,
            AvoidSpringRepositoryCallInLoopCheck.class,
            AvoidSQLRequestInLoop.class,
            AvoidFullSQLRequest.class,
            UseCorrectForLoop.class,
            UnnecessarilyAssignValuesToVariables.class,
            OptimizeReadFileExceptions.class,
            InitializeBufferWithAppropriateSize.class,
            AvoidUsingGlobalVariablesCheck.class,
            AvoidSetConstantInBatchUpdate.class,
            FreeResourcesOfAutoCloseableInterface.class,
            AvoidMultipleIfElseStatement.class,
            OptimizeDatabaseQueries.class
    );

    /**
     * Register the classes that will be used to instantiate checks during analysis.
     */
    @Override
    public void register(RegistrarContext registrarContext) {
        // Call to registerClassesForRepository to associate the classes with the correct repository key
        registrarContext.registerClassesForRepository(JavaRulesDefinition.REPOSITORY_KEY, checkClasses(), testCheckClasses());
    }

    /**
     * Lists all the main checks provided by the plugin
     */
    public static List<Class<? extends JavaCheck>> checkClasses() {
        return ANNOTATED_RULE_CLASSES;
    }

    /**
     * Lists all the test checks provided by the plugin
     */
    public static List<Class<? extends JavaCheck>> testCheckClasses() {
        return Collections.emptyList();
    }
}
