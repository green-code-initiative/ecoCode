## Prérequis

- Java >= 8
- Mvn 3

## Installation
Installer les dépendances maven depuis la racine du project :

`mvn clean install`

## Liens
- Tuto Java : https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md
- Tuto Python : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/python-custom-rules
- Tuto Php : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/php-custom-rules

## Architecture du projet
Voici un aperçu de l'architecture du projet :
```
native-analyzer      # Répertoire du project maven des plugins dit "natif" saonarqube
|  |
+--java-plugin       # Contiens le module JAVA
|  |
|  +--src
|  |  |
|  |  +--main
|  |  |  |
|  |  |  +--java/fr/cnumr/java
|  |  |  |  |
|  |  |  |  +--checks
|  |  |  |  +--utils
|  |  |  |  +--MyJavaFileCheckRegistrar.java
|  |  |  |  +--MyJavaRulesDefinition.java
|  |  |  |  +--MyJavaRulesPlugin.java
|  |  |  |  +--RulesList.java
|  |  |  +--ressources
|  |  |  |  |
|  |  |  |  +--fr/cnumr/l10n/java/rules/java
|  |  |  |  |
|  |  +--test
|  |  |  |
|  |  |  +--files
|  |  |  |
|  |  |  +--java/fr/cnumr/java
+--php-plugin        # Contiens le module PHP
|  |
|  +--src
|  |  |
|  |  +--main
|  |  |  |
|  |  |  +--java/fr/cnumr/php
|  |  |  |  |
|  |  |  |  +--checks
|  |  |  |  +--MyPhpRules.java
|  |  |  |  +--PHPCustomRulesPlugin.java
|  |  |  +--ressources
|  |  |  |  |
|  |  |  |  +--fr/cnumr/l10n/php/rules/custom
|  |  |  |  |
|  |  +--test
|  |  |  |
|  |  |  +--java/fr/cnumr/php
|  |  |  |
|  |  |  +--ressources
\--python-plugin     # Contient le module Python
|  |
|  +--src
|  |  |
|  |  +--main
|  |  |  |
|  |  |  +--java/fr/cnumr/python
|  |  |  |  |
|  |  |  |  +--checks
|  |  |  |  +--CustomPythonRuleRepository.java
|  |  |  |  +--CustomPythonRulesPlugin.java
|  |  |  +--ressources
|  |  |  |  |
|  |  |  |  +--fr/cnumr/l10n/python/rules/python
|  |  |  |  |
|  |  +--test
|  |  |  |
|  |  |  +--java/fr/cnumr/python
|  |  |  |
|  |  |  +--ressources
\--docker-compose.yml   # Docker compose file qui installe automatiquement les plugins natifs si ces derniers ont bien été générer cf // TODO
```

Vous pouvez plus d'informations sur l'architecture des différents linters et plugins natifs dans leurs README respectifs.











