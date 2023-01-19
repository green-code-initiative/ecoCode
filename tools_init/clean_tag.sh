#!/bin/bash

. _core.sh

debug "SONAR_TOKEN : $SONAR_TOKEN"
debug "SONAR_URL : $SONAR_URL"
debug "RULES_KEYS : $RULES_KEYS"
debug "TAG_ECOCONCEPTION : $TAG_ECOCONCEPTION"

# check SonarQube API connection
check_sonarapi

declare -i nb_rules_updated=0

# loop on each rule key to check and clean the tag if found
for rule_key in "${RULES_ARRAY[@]}"
do
  echo -e "\n*****  Processing rule ${BLUE}$rule_key${NC}  *****"

  # get rule data from Sonar API
  res_json=$(find_rule_sonarapi $rule_key)
  echo -e "Existing sysTags : $(echo "$res_json" | jq -r '.rules[].sysTags')"
  echo -e "Existing tags : $(echo "$res_json" | jq -r '.rules[].tags')"

  # loop on tags array to check if tag is present
  # secondly, creating all tags list to delete the newone inside if new tag is found
  declare tags_string=""
  is_tag_found=0
  while read -r tag ; do
    debug "  Processing tag $tag"

    if [ "$tag" == "$TAG_ECOCONCEPTION" ]; then
      is_tag_found=1
    else
      # only keep tags aren't the newone
      if [ -n "$tags_string" ]; then
        tags_string+=","
      fi
      tags_string+="$tag"
    fi
  done < <(echo "$res_json" | jq -r '.rules[].tags[]')

  # check if tag was found on tags array
  # if found, use built tag list (without tag) and call Sonar API to update tags of the current rule
  if [ $is_tag_found == 1 ]; then
    echo -e "  Tag ${MAGENTA}$TAG_ECOCONCEPTION${NC} ${GREEN}FOUND${NC} in tags array : we will delete it from rule '$rule_key'"

    update_rule_sonarapi "$rule_key" "$tags_string"

    nb_rules_updated+=1
  else
    echo -e "  Tag ${MAGENTA}$TAG_ECOCONCEPTION${NC} ${RED}NOT FOUND${NC} in tags array : no need to delete it"
  fi

done

echo -e "\n==> ${GREEN}$nb_rules_updated${NC} rules cleaned\n"
