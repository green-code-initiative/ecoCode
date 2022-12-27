#!/usr/bin/env sh

mvn release:prepare -B -ff -DpushChanges=false -DtagNameFormat=@{project.version}
