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
 * Tests for FatAppRule
 *
 * @author Leboulanger Mickael
 */
class FatAppRuleTest extends AbstractRuleTestCase<FatAppRule> {

    @Test
    void test_RuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'FatApp'
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
    void testDetect_multiDexEnabled_true() {
        final SOURCE = '''
           android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersionVersionVersion 28
                    targetSdkVersionVersionVersion 32
                    versionCode 1
                    versionName "1.0"
                    multiDexEnabled true

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
        assertSingleViolation(SOURCE, 11, 'multiDexEnabled', getViolationMessage())
    }

    @Test
    void testDetect_multiDexEnabled_variable_true_before() {
        final SOURCE = '''
            def multidex = true
            android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersionVersion 28
                    targetSdkVersionVersion 32
                    versionCode 1
                    versionName "1.0"
                    multiDexEnabled multidex

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
        assertSingleViolation(SOURCE, 12, 'multiDexEnabled', getViolationMessage())
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
                    multiDexEnabled multidex

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
            def multidex = true
        '''
        assertSingleViolation(SOURCE, 11, 'multiDexEnabled', getViolationMessage())
    }

    @Test
    void testDetect_multiDexEnabled_variable_may_be_true() {
        final SOURCE = '''
            def multidex = false
            android {
                compileSdk 32

                defaultConfig {
                    applicationId "com.example.sampleForSonar"
                    minSdkVersion 28
                    targetSdkVersion 32
                    versionCode 1
                    versionName "1.0"
                    multiDexEnabled multidex

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
            if (true) {
                multidex = true
            }
        '''
        assertSingleViolation(SOURCE, 12, 'multiDexEnabled', getViolationMessage())
    }

    @Override
    protected FatAppRule createRule() {
        new FatAppRule()
    }
    private String getViolationMessage() {
        return 'Using "multiDexEnabled true" goes against the overall reduction of the weight of the apps and hence must be avoided.'
    }
}
