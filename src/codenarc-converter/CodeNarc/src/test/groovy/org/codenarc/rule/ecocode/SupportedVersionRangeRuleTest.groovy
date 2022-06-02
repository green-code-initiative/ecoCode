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

import org.codenarc.rule.AbstractRuleTestCase
import org.junit.Test

/**
 * Tests for SupportedVersionRangeRule
 *
 * @author Leboulanger Mickael
 */
class SupportedVersionRangeRuleTest extends AbstractRuleTestCase<SupportedVersionRangeRule> {

    @Test
    void test_RuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'SupportedVersionRange'
    }

    @Test
    void test_SomeCondition_NoViolations() {
        final SOURCE = '''
           android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 28
                    targetSdkVersion 31
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled false
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro\'
                    }
                }
                compileOptions {
                    sourceCompatibility JavaVersion.VERSION_1_8
                    targetCompatibility JavaVersion.VERSION_1_8
                }
                buildFeatures {
                    viewBinding true
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    @Test
    void test_SdkRange_SingleViolation() {
        final SOURCE = '''
           android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 26
                    targetSdkVersion 31
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled false
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro\'
                    }
                }
                compileOptions {
                    sourceCompatibility JavaVersion.VERSION_1_8
                    targetCompatibility JavaVersion.VERSION_1_8
                }
                buildFeatures {
                    viewBinding true
                }
            }
        '''
        assertSingleViolation(SOURCE,8, 'targetSdkVersion')
    }

    @Override
    protected SupportedVersionRangeRule createRule() {
        new SupportedVersionRangeRule()
    }
}
