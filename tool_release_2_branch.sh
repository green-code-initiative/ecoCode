#!/usr/bin/env sh

###
# PURPOSE : create an push an new branch with commits previously prepared with `tool_release_1_prepare.sh`
###

# checkout released tag and creation of branch to push (becasue of main protection)
LAST_TAG=$(git tag --sort=-version:refname | head -n 1)
BRANCH_NAME=$(echo "release_${LAST_TAG}")
git checkout -b ${BRANCH_NAME}

# push branch associated to new tag release
git push --set-upstream origin ${BRANCH_NAME}
