#!/bin/bash

#echo "Please do the check on Sonar UI : click on 'rules' tab, then 'tag' menu on the left, then enter tag name (or a part of name) on search field => You will have all rues with that tag"

. _core.sh

debug "SONAR_TOKEN : $SONAR_TOKEN"
debug "SONAR_URL : $SONAR_URL"
debug "RULES_KEYS : $RULES_KEYS"
debug "TAG_ECOCONCEPTION : $TAG_ECOCONCEPTION"

# check SonarQube API connection
check_sonarapi

# loop on each rule key to add the tag
for rule_key in "${RULES_ARRAY[@]}"
do
  echo -e "\n*****  Checking rule ${BLUE}$rule_key${NC}  *****"
  res_json=$(find_rule_sonarapi $rule_key)
  echo -e "Tags found : $(echo "$res_json" | jq -r '.rules[].tags')"
  echo -e "SysTags found : $(echo "$res_json" | jq -r '.rules[].sysTags')"
done
