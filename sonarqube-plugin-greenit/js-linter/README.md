# eslint-plugin-green-it

Linter JS du plugin sonar green IT. Linter basé sur eslint.

## Prerequis 
- Node >= 10

## Liens

- https://eslint.org/docs/user-guide/getting-started
- https://eslint.org/docs/developer-guide/working-with-rules

## Getting started

Installer les dépendances du project :

`npm i`

Installer yoman

`npm install --global yo`

Installer le generateur eslint :

`npm i -g generator-eslint`

### Ajouter de nouvelles règles

Pour ajouter une nouvelle règle utiliser le générateur eslint de yo :

![img_3.png](../images/img_5.png)

Le génerateur va vous génerer 3 fichiers :

- Un fichier de documentation
- Un fichier pour votre règle 
- Un fichier test qui intégrera le cas de test

Pour tester votre règle exécuter la commande suivante :

`npm test`

### Génerer et importer le rapport dans sonarqube 

Considérons que vous vous trouvez dans le répertoire du projet. Exécuter la commande :

`eslint  -c ./.eslintrc.json $PATH_DU_PROJET_CIBLE -f json > eslint-report.json`

## Architecture

Voici un aperçu de l'architecture du projet :
```

js-linter        # Dossier racine du projet de linter JS
|
+--docs           # Repertoire de la documentation
|  |
|  +--rules       # Repertoire contenant toutes les desctiptions des règles
+--lib            
|  |
|  +--rules      # Repertoire contenant l'enssemble des rules
|  |
|  +--index.js   # fichiers d'entrées du plugin qui importe toutes les règles
+--tests     # Répertoire de test
|  |
|  +--lib       
|  |  |
|  |  +--rules        # Répertoire contenant l'intégralité des tests du projet
|
+--package.json  
\--.eslintrc.json   # Fichier de configuration du plugin
```

## Installation

You'll first need to install [ESLint](http://eslint.org):

```
$ npm i eslint --save-dev
```

Next, install `eslint-plugin-green-it`:

```
$ npm install eslint-plugin-green-it --save-dev
```


## Usage

Add `green-it` to the plugins section of your `.eslintrc` configuration file. You can omit the `eslint-plugin-` prefix:

```json
{
    "plugins": [
        "green-it"
    ]
}
```


Then configure the rules you want to use under the rules section.

```json
{
    "rules": {
        "green-it/rule-name": 2
    }
}
```

## Supported Rules

* Fill in provided rules here





