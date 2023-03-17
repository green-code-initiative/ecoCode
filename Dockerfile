FROM maven:3-openjdk-11-slim AS builder

COPY . /usr/src/ecocode

WORKDIR /usr/src/ecocode
RUN ./tool_build.sh

FROM sonarqube:9.9-community
COPY --from=builder /usr/src/ecocode/lib/* /opt/sonarqube/extensions/plugins/
