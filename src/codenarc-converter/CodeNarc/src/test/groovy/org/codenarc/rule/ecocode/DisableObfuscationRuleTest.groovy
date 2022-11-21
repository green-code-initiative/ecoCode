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

import org.junit.Test
import org.codenarc.rule.AbstractRuleTestCase

/**
 * Tests for DisableObfuscationRule
 *
 * @author Leboulanger Mickael
 */
class DisableObfuscationRuleTest extends AbstractRuleTestCase<DisableObfuscationRule> {

    @Test
    void test_RuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'DisableObfuscation'
    }

    @Test
    void test_SomeCondition_NoViolations() {
        final SOURCE = '''
           android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 28
                    targetSdkVersion 32
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
    void testDetect_minifyEnabled_true() {
        final SOURCE = '''
           android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersionVersionVersion 28
                    targetSdkVersionVersionVersion 32
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled true
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
        assertSingleViolation(SOURCE, 17, 'minifyEnabled', getViolationMessage())
    }

    @Test
    void testDetect_minifyEnabled_variable_true_before() {
        final SOURCE = '''
            def minify = true
            android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersionVersion 28
                    targetSdkVersionVersion 32
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled minify
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
        assertSingleViolation(SOURCE, 18, 'minifyEnabled', getViolationMessage())
    }

    @Test
    void testDetect_multiDexEnabled_variable_true_after() {
        final SOURCE = '''
            android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 28
                    targetSdkVersion 32
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled minify
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
            def minify = true
        '''
        assertSingleViolation(SOURCE, 17, 'minifyEnabled', getViolationMessage())
    }

    @Test
    void testDetect_minifyEnabled_variable_may_be_true() {
        final SOURCE = '''
            def minify = false
            android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 28
                    targetSdkVersion 32
                    versionCode 1
                    versionName "1.0"

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled minify
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
            if (true) {
                minify = true
            }
        '''
        assertSingleViolation(SOURCE, 18, 'minifyEnabled', getViolationMessage())
    }

    @Override
    protected DisableObfuscationRule createRule() {
        new DisableObfuscationRule()
    }
    private String getViolationMessage() {
        return 'Using minifyEnabled true will obfuscate code and will have a sligthly negative impact on power consumption at runtime.'
    }
}
