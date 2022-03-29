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

import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.util.AstUtil

/**
 * When looking at the minSdkVersion and targetSdkVersion attributes for the <uses-sdk> in the AndroidManifest.xml file, the amplitude of supported platform versions should not be too wide, at the risk of making the app too heavy to handle all cases.
 *
 * @author Leboulanger Mickael
 */
class SupportedVersionRangeRule extends AbstractAstVisitorRule {

    String name = 'SupportedVersionRange'
    int priority = 2
    int minSdkVersion = 0
    int targetSdkVersion = 0
    int threshold = 4 // Value used to compare minSdkVersion and targetSdkVersion
    Class astVisitorClass = SupportedVersionRangeAstVisitor
}

class SupportedVersionRangeAstVisitor extends AbstractAstVisitor {

    @Override
    void visitMethodCallExpression(MethodCallExpression methodCallExpression) {
        def methodName = ((ConstantExpression) methodCallExpression.getMethod()).getValue()
        if (methodName == 'minSdkVersion' || methodName == 'targetSdkVersion') {
            def arguments = AstUtil.getArgumentsValue(methodCallExpression.getArguments())
            if (arguments.size() == 1) {
                rule[methodName] = arguments.get(0)
            }
            if ((rule.minSdkVersion && rule.targetSdkVersion) && rule.targetSdkVersion - rule.minSdkVersion > rule.threshold) {
                addViolation(methodCallExpression, getViolationMessage())
            }
        }
        super.visitMethodCallExpression(methodCallExpression)
    }

    private String getViolationMessage() {
        return 'The amplitude of supported platform versions should not be too wide, at the risk of making the app too heavy to handle all cases.'
    }
}
