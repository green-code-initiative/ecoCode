#!/bin/bash

. _core.sh

debug "SONAR_TOKEN : $SONAR_TOKEN"
debug "SONAR_URL : $SONAR_URL"
debug "RULES_KEYS : $RULES_KEYS"
debug "TAG_ECOCONCEPTION : $TAG_ECOCONCEPTION"

# check SonarQube API connection
check_sonarapi

declare -i nb_rules_updated=0

# loop on each rule key to check and add the tag if not found
for rule_key in "${RULES_ARRAY[@]}"
do
  echo -e "\n*****  Processing rule ${BLUE}$rule_key${NC}  *****"

  # get rule data from Sonar API
  res_json=$(find_rule_sonarapi $rule_key)
  echo -e "Existing sysTags : $(echo "$res_json" | jq -r '.rules[].sysTags')"
  echo -e "Existing tags : $(echo "$res_json" | jq -r '.rules[].tags')"

  # loop on systags array to check if tag is already present
  is_systag_found=0
  while read -r systag ; do
    debug "  Processing systag $systag"
    if [ "$systag" == "$TAG_ECOCONCEPTION" ]; then
      is_systag_found=1
      break;
    fi
  done < <(echo "$res_json" | jq -r '.rules[].sysTags[]')

  # check if tag was found on sysTags array (sysTags not editable)
  if [ $is_systag_found == 1 ]; then
    echo -e "  Tag ${MAGENTA}$TAG_ECOCONCEPTION${NC} ${GREEN}already exists${NC} in systags array"
    debug "Neither need to add tag nor check tags array"
    continue
  fi

  # here : tag not found inside systags array

  # loop on tags array to check if tag is already present
  # secondly, creating all tags list to add the newone inside if new tag has to be added
  declare tags_string=""
  is_tag_found=0
  while read -r tag ; do
    debug "  Processing tag $tag"
    if [ -n "$tags_string" ]; then
      tags_string+=","
    fi
    tags_string+="$tag"

    if [ "$tag" == "$TAG_ECOCONCEPTION" ]; then
      is_tag_found=1
      # don't break the loop here (like systags loop above) because we have to build complete tags_string
    fi
  done < <(echo "$res_json" | jq -r '.rules[].tags[]')

  # check if tag was found on tags array
  # if not found, add the new tag to actual tag list and call Sonar API to update tags of the current rule
  if [ $is_tag_found == 0 ]; then
    echo -e "  Tag ${MAGENTA}$TAG_ECOCONCEPTION${NC} ${GREEN}NOT FOUND${NC} in tags array nor in systags array : we will add it to rule '$rule_key'"

    if [ -n "$tags_string" ]; then
      tags_string+=","
    fi
    tags_string+="$TAG_ECOCONCEPTION"

    update_rule_sonarapi "$rule_key" "$tags_string"

    nb_rules_updated+=1
  else
    echo -e "  Tag ${MAGENTA}$TAG_ECOCONCEPTION${NC} ${RED}already exists${NC} in tags array"
  fi

  # tag_ecoconception=$(echo "$res_json" | jq --arg TAG_ECOCONCEPTION -r '.rules[] | select( .tags | index("$TAG_ECOCONCEPTION"))')
  # echo "tag_ecoconception : ${tag_ecoconception}"

done

echo -e "\n==> ${GREEN}$nb_rules_updated${NC} rules updated\n"
