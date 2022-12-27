#!/bin/bash

. _core.sh

debug "SONAR_TOKEN : $SONAR_TOKEN"
debug "SONAR_URL : $SONAR_URL"
debug "RULES_KEYS : $RULES_KEYS"
debug "TAG_ECOCONCEPTION : $TAG_ECOCONCEPTION"
debug "PROFILE_ECOCONCEPTION : $PROFILE_ECOCONCEPTION"
debug "PROFILES_LANGUAGE_KEYS : $PROFILES_LANGUAGE_KEYS"

# check SonarQube API connection
check_sonarapi

declare -i nb_profile_update=0

# loop on each profile to create a fork SonarWay with + New rules ecoconception
for language in "${PROFILES_LANGUAGE_ARRAY[@]}"
do
  echo -e "\n*****  Processing profile language ${BLUE}$language${NC}  *****"

  create_profile_sonarapi "$language" "$PROFILE_ECOCONCEPTION"
  change_parent_profile_sonarapi "$language" "$PROFILE_ECOCONCEPTION"


  # get profile data from Sonar API
  res_json=$(search_profile_sonarapi $language $PROFILE_ECOCONCEPTION)
  key_profile=$(echo "$res_json" | jq -r '.profiles[].key')

  activate_rules_ecocode_profile_sonarapi "$language" "$key_profile" "$TAG_ECOCONCEPTION"

  nb_profile_update+=1
done

echo -e "\n==> ${GREEN}$nb_profile_update${NC} profiles updated\n"
