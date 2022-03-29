## Prerequisites

- Java >= 8
- Mvn 3

## Installation

The Android plugin uses CodeNarc to scan the gradle files of Android projects. To have more information about CodeNarc: [CodeNarc](/codenarc-converter/CodeNarc/README.md).

CodeNarc must be built separately. Please see the following steps:

Build CodeNarc (gradle 6.5.1, Java 14):

```sh
./gradlew build -x test 
```

Add custom CodeNarc to Maven dependencies:

```sh
cd /codenarc-converter
mvn initialize
```

Build codenarc-converter:

```sh
cd /codenarc-converter
mvn clean package
```

Install dependencies and build plugins from the root directory
```sh
mvn clean install
```

.jar files (one per plugin) will be moved in `target/lib` repository after build.

## Links
- Java how-to : https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md
- Python how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/python-custom-rules
- PHP how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/php-custom-rules








