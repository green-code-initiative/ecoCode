## Prerequisites

- Java >= 8
- Mvn 3

## Installation
Install dependencies from the root directory

`mvn clean install`

.jar files (one per plugin) will be moved in `target/lib` repository after build.

Codenarc has to be build separately using :

- In sonarqube-plugin-greenit/native-analyzer/codenarc-converter/CodeNarc (gradle 6.5.1, java 14)

```sh
gradle build -x test 
```

- In sonarqube-plugin-greenit/native-analyzer/codenarc-converter

```sh
mvn clean package
```

## Links
- Java how-to : https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md
- Python how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/python-custom-rules
- PHP how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/php-custom-rules








