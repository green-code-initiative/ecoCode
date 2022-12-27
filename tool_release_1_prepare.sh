#!/usr/bin/env sh

# creation of 2 commits with release and next SNAPSHOT
mvn release:prepare -B -ff -DpushChanges=false -DtagNameFormat=@{project.version}

# clean temporary files
mvn release:clean
