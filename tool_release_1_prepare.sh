#!/usr/bin/env sh

###
# PURPOSE : use maven plugin release to prepare locally next release and next SNAPSHOT
###

# creation of 2 commits with release and next SNAPSHOT
mvn release:prepare -B -ff -DpushChanges=false -DtagNameFormat=@{project.version}

sleep 2

# clean temporary files
mvn release:clean
