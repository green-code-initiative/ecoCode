#!/usr/bin/env sh

rm -rf ./lib/*.jar
mvn clean package -DskipTests
