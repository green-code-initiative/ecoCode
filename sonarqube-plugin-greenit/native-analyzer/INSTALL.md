## Prerequisites

- Java >= 8
- Mvn 3

## Installation

For now, [CodeNarc](/codenarc-converter/CodeNarc/README.md) implementation requires you to build it separately.

Build CodeNarc (gradle 6.5.1, Java 14):

```sh
gradle build -x test 
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

Build android-plugin:

```sh
cd /android-plugin
mvn clean package
```

Install dependencies from the root directory

```sh
mvn clean install
```


.jar files (one per plugin) will be moved in `target/lib` repository after build.

For more details see CodeNarc-converter [README](/codenarc-converter/README.md).

## Links
- Java how-to : https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md
- Python how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/python-custom-rules
- PHP how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/php-custom-rules








