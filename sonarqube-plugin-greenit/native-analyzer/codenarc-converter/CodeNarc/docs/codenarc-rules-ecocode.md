---
layout: default
title: CodeNarc - Ecocode Rules
---  

# Ecocode Rules  ("*rulesets/ecocode.xml*")

## FatApp Rule

*Since CodeNarc 2.2.1*

Using "multiDexEnabled true" goes against the overall reduction of the weight of the apps and hence must be avoided.

Example of violations:

```
    android {
        compileSdk 32

        defaultConfig {
            applicationId "com.example.sampleForSonar"
            minSdkVersion 28
            targetSdkVersion 32
            versionCode 1
            versionName "1.0"
            multiDexEnabled true


            testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
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
```

## SupportedVersionRange Rule

*Since CodeNarc 2.2.1*

When looking at the minSdkVersion and targetSdkVersion attributes for the <uses-sdk> in the AndroidManifest.xml file, the amplitude of supported platform versions should not be too wide, at the risk of making the app too heavy to handle all cases.

Example of violations:

```
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
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
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
```
