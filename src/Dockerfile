FROM maven:3-openjdk-11-slim AS maven

LABEL org.opencontainers.image.source https://github.com/cnumr/ecoCode

COPY . /usr/src/ecocode

WORKDIR /usr/src/ecocode
RUN ./prepare-codenarc
RUN mvn clean install

FROM sonarqube:9.3-community
COPY --from=maven /usr/src/ecocode/java-plugin/target/ecocode-java-plugin-1.0.0-SNAPSHOT.jar /opt/sonarqube/extensions/plugins/
COPY --from=maven /usr/src/ecocode/php-plugin/target/ecocode-php-plugin-1.0.0-SNAPSHOT.jar /opt/sonarqube/extensions/plugins/
COPY --from=maven /usr/src/ecocode/python-plugin/target/ecocode-python-plugin-1.0.0-SNAPSHOT.jar /opt/sonarqube/extensions/plugins/
COPY --from=maven /usr/src/ecocode/android-plugin/target/ecocode-rules-1.0-SNAPSHOT.jar /opt/sonarqube/extensions/plugins/
