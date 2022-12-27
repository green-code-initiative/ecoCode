#!/usr/bin/env sh

mvn release:prepare -B -ff -DpushChanges=false -DtagNameFormat=@{project.version}
mvn release:clean
LAST_TAG=$(git tag --sort=-version:refname | head -n 1)
BRANCH_NAME=$(echo "release_${LAST_TAG}")
git checkout -b ${BRANCH_NAME}
git push --set-upstream origin ${BRANCH_NAME}