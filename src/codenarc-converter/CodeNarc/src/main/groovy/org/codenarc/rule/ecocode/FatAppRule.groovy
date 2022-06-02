/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.ecocode

import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.source.SourceCode
import org.codenarc.util.AstUtil

/**
 * Using "multiDexEnabled true" goes against the overall reduction of the weight of the apps and hence must be avoided.
 *
 * @author Leboulanger Mickael
 */
class FatAppRule extends AbstractAstVisitorRule {

    String name = 'FatApp'
    int priority = 2
    Class astVisitorClass = FatAppAstVisitor
}

class FatAppAstVisitor extends AbstractAstVisitor {

    @Override
    void visitMethodCallExpression(MethodCallExpression methodCallExpression) {
        if (((ConstantExpression) methodCallExpression.getMethod()).getValue() == 'multiDexEnabled') {
            for (Object value : AstUtil.getArgumentsValue(methodCallExpression.getArguments())) {
                if (value == true)
                    addViolation(methodCallExpression, getViolationMessage())
                else { // TODO get value from variable at runtime
                    this.getSourceCode().getLines().each { string ->
                        if (string.contains(value + ' = true')) {
                            addViolation(methodCallExpression, getViolationMessage())
                        }
                    }
                }
            }
        }
        super.visitMethodCallExpression(methodCallExpression)
    }

    private String getViolationMessage() {
        return 'Using \"multiDexEnabled true\" goes against the overall reduction of the weight of the apps and hence must be avoided.'
    }
}
