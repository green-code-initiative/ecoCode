
### Prerequisites

- Docker
- Docker-compose 3.9

## Project structure

Here is a preview of project tree :

```
Ecocode              # Root directory of "native" linter
|
+--android-plugin    # Android
|
+--java-plugin       # JAVA
|
+--php-plugin        # PHP
|
+--python-plugin     # Python
|
\--docker-compose.yml   # Docker compose file installing available analyzer // TODO
```

You will find more information about the plugins’ architecture in their folders


## Howto install SonarQube dev environment

### Pre-requisites

The project relies on a version of [CodeNarc](https://codenarc.org/) enriched with specific rules.
To build this version and make it available to Maven, use the following steps:

```sh
cd codenarc-converter/CodeNarc
./gradlew build -x test
mvn install:install-file -Dfile=build/libs/CodeNarc-2.2.2.jar -DgroupId=org.codenarc -DartifactId=CodeNarc -Dversion=2.2.2 -Dpackaging=jar 
cd ../..
```

### Init

Build Code

```sh
mvn clean install
```

Run the stack

```sh 
docker-compose up --build -d
```

Check if the container are up

```sh 
docker ps
```

You should see two lines (one for sonarQube:latest and one for postgres). If there is only postgres, check the logs

```sh 
docker ps -a
docker logs src_sonar_1
```

If you have this error on run

`web_1  | [1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`

You can allow more virtual memory:

```sh
sudo sysctl -w vm.max_map_count=262144
```

For windows
```cmd
wsl -d docker-desktop
sysctl -w vm.max_map_count=262144
```


Go to http://localhost:9000 and use these credentials :
```
login: admin
password: admin
```

When you are connected, generate a new token :

`My Account -> Security -> Generate Tokens`

![img.png](images/img.png)
![img_1.png](images/img_1.png)



Start again your services using the token :

```sh
TOKEN=MY_TOKEN docker-compose up --build -d
```

## Howto install Plugin Ecocode

The Android plugin uses CodeNarc to scan the gradle files of Android projects. To have more information about CodeNarc: [CodeNarc](/codenarc-converter/CodeNarc/README.md).

CodeNarc must be built separately. Please see the following steps:

Build CodeNarc (gradle 6.9.2, Java 11):

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

## Prerequisites

- Java >= 8
- Mvn 3

## Installation
Install dependencies from the root directory

`mvn clean install`

.jar files (one per plugin) will be moved in `target/lib` repository after build.

## Links
- Java how-to : https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md
- Python how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/python-custom-rules
- PHP how-to : https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/php-custom-rules

