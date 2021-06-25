## Plugin SonarQube d'éco-conception

Les bonnes pratiques présentes dans le plugin sont celles de la 2ème édition du référentiel paru en septembre 2015. 
Pour connaitre l'intégralité des règle d'eco-conception actuel visiter le [lien](https://docs.google.com/spreadsheets/d/1nujR4EnajnR0NSXjvBW3GytOopDyTfvl3eTk2XGLh5Y/edit#gid=1386834576)

## Fonctionnement d'un plugin sonar 
Un plugin sonar commence par parser vos fichiers en AST . L’AST vous permettra par la suite d’accéder directement à un ou plusieurs nœud de l’arbre que vous désirez.
Par exemple il vous permettra  d’accéder à toutes les boucles for et d’explorer leurs déclaration, leurs block etc …
L’AST diffère selon les langages même si une bonne partie reste très similaire. 
Pour vous familiariser avec l’AST de votre langage et mieux comprendre comment la structure de votre fichier vous pouvez utilisez [AST explorer](https://astexplorer.net/)
## Liens
 - https://docs.sonarqube.org/latest/analysis/overview/

### Prerquis

- Docker
- Docker-compose 3.9

## Architecture du projet
Voici un aperçu de l'architecture du projet :
```

sonarQubeGreenIt        # Dossier racine du projet (non versionner) contient l'ensemble du projet en lui même
|
+--css-linter           # Repertoire du linter CSS 
|  |
+--js-linter            # Repertoire du linter JS
|  |
+--native-analyzer      # Répertoire du project maven des plugins dit "natif" saonarqube
|  |
|  +--java-plugin       # Contiens le module JAVA
|  |
|  +--php-plugin        # Contiens le module PHP
|  |
|  \--python-plugin     # Contient le module Python
|
\--docker-compose.yml   # Docker compose file qui installe automatiquement les plugins natifs si ces derniers ont bien été générer cf // TODO
```

Vous pouvez plus trouver plus d'informations sur l'architecture des différents linters et plugins natifs dans leurs README respectifs.


## Installation de l'environnement SonarQube de développmement 

### Initialisation
Démarrer sonar `docker-compose up`

Si vous rencontrez l'erreur suivante en démmarant :

`web_1  | [1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`

Vous pouvez augmentez la taille mémoire virtuelle :

`sudo sysctl -w vm.max_map_count=262144
`

Se connecter une première fois http://localhost:9000 avec les identifiants :
`login: admin
password: admin`

Une fois connecter générez un token d'authentification :

My Account -> Security -> Generate Tokens
![img.png](images/img.png)
![img_1.png](images/img_1.png)



Relancez vos services en renseignant le token :

`TOKEN=MY_TOKEN docker-compose up`


### Import des plugins

#### Analyzer natifs

Les analyzers natifs seront directement importés dans le sonarqube lors de build docker à condition que vous ayez bien effectué
[l'installation](https://github.com/p2lvoizinDavidson/sonarQubeGreenIt/tree/sonarPracticePR/native-analyzer#installation)

#### Linter

Pour les linters il est nécessaire de générer un rapport qui sera ensuite interprété par l'analyzer sonar.
Pour générer ce rapport réferencez vous au README des linters respectifs :

- [CSS](https://github.com/p2lvoizinDavidson/sonarQubeGreenIt/tree/sonarPracticePR/css-linter#g%C3%A9nerer-et-importer-le-rapport-dans-sonarqube)
- [JS](https://github.com/p2lvoizinDavidson/sonarQubeGreenIt/tree/sonarPracticePR/js-linter#g%C3%A9nerer-et-importer-le-rapport-dans-sonarqube)


Attention le contenu des rapports fait référence aux fichiers qu'il a analysés.
il est important que le path de l'analyse de votre linter corresponde au path du fichier sur le sonar.
Pour ce faire par default placer votre projet à analyser dans un répertoire `/opt/project` et placer y aussi vos rapports d'analyses dans ce répertoire.

Si vous souhaitez utiliser un autre repertoire libre à vous de modifier le docker compose.

### Profile

Quality Profiles -> Create
Remplissez les informations suivantes, le mieux est de repartir de Sonar et d'y activer les règles d'eco-conception :

![img_2.png](images/img_2.png)

Une fois le profile créé activer les règles d'eco-conception

![img_3.png](images/img_3.png)


Une fois le profil complet réferencé le comme le profil par defaut du langage.

![img_4.png](images/img_4.png)

Desormais votre environement sonarqube est configuré !
## Comment contribuer

## Auteur

Gaël Pellevoizin

## Licence











