#!/bin/bash

. _config.sh

###########################################################################################
#    C O R E     ( /!\ /!\ /!\ NOT to modify)
###########################################################################################
MAGENTA='\033[38;5;201m'
PINK='\033[38;5;219m'
BLUE='\033[38;5;39m'
GREY='\033[38;5;235m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

### display debug logs if DEBUG enabled
### $1 : message to log
debug() {
  if [ $DEBUG == 1 ]; then
    echo -e "${GREY}--- DEBUG --- $1${NC}"
  fi
}

### build array with all rules
### $1 : the variable to set into the computed response (using it by reference)
declare -a RULES_ARRAY
function build_rules_array {
  # declare ARRAY_RESPONSE=$1
  orig_IFS="$IFS"
  IFS=','
  read -ra RULES_ARRAY <<< "$RULES_KEYS"
  IFS="${orig_IFS}"
}

### build array with each quality profile language to init/update
declare -a PROFILES_LANGUAGE_ARRAY
function build_profiles_language_array {
  orig_IFS="$IFS"
  IFS=','
  read -ra PROFILES_LANGUAGE_ARRAY <<< "$PROFILES_LANGUAGE_KEYS"
  IFS="${orig_IFS}"
}

function check_sonarapi() {
  echo -e "\n*****  Checking SonarQube connection  *****"
  declare -a http_code=`curl -I -s -w "%{http_code}\n" -o /dev/null -u $SONAR_TOKEN: --request GET $SONAR_URL/api/system/ping`
  if [ $http_code != "200" ]; then
    echo "SonarQube connection error : $http_code"
    exit 1
  else
    debug "SonarQube connection ok : $http_code"
  fi
}

### find one rule using SonarQube API
### $1 : rule key
function find_rule_sonarapi() {
  # echo "Execution of command : curl -u $SONAR_TOKEN: --request GET \"$SONAR_URL/api/rules/search?rule_key=$1\" 2>/dev/null"
  echo $(curl -u $SONAR_TOKEN: --request GET "$SONAR_URL/api/rules/search?rule_key=$1" 2>/dev/null)
}

### update one rule with tags list using SonarQube API
### $1 : rule key
### $2 : tags list in string format separated by comma
function update_rule_sonarapi() {
  # echo $(curl -u $SONAR_TOKEN: --request POST $SONAR_URL/api/rules/update?key=$1&tags=$2 2>/dev/null)
  echo "Execution of command : curl -u $SONAR_TOKEN: -s -o /dev/null --request POST \"$SONAR_URL/api/rules/update?key=$1&tags=$2\""
  if [ $SIMULATION == 0 ]; then
    echo -e "CALL to Sonar API to add tag"
    curl -u $SONAR_TOKEN: -s -o /dev/null --request POST "$SONAR_URL/api/rules/update?key=$1&tags=$2"
  else
    echo "   _-_-_-_-_   SIMULATION MODE _-_-_-_-_ (no real call to sonar API)"
  fi
}

### search profile using SonarQube API
### $1 :  language
### $2 :  qualityProfile (name)
function search_profile_sonarapi() {
    echo $(curl -u $SONAR_TOKEN: --request GET "$SONAR_URL/api/qualityprofiles/search?language=$1&qualityProfile=$2" 2>/dev/null)
}

### create profile using SonarQube API
### $1 :  language
### $2 :  name (qualityProfile)
function create_profile_sonarapi() {
    echo $(curl -u $SONAR_TOKEN: --request POST "$SONAR_URL/api/qualityprofiles/create?language=$1&name=$2" 2>/dev/null)
}

### change parent profile with Sonar Way using SonarQube API
### $1 :  language
### $2 :  qualityProfile (name)
function change_parent_profile_sonarapi(){
    echo $(curl -u $SONAR_TOKEN: --request POST "$SONAR_URL/api/qualityprofiles/change_parent?language=$1&qualityProfile=$2&parentQualityProfile=Sonar+way" 2>/dev/null)
}

### add new rules with label eco-conception from plugins using SonarQube API
### $1 :  language
### $2 :  key quality Profile
### $3 :  tags list in string format separated by comma
function activate_rules_ecocode_profile_sonarapi(){
    echo $(curl -u $SONAR_TOKEN: --request POST "$SONAR_URL/api/qualityprofiles/activate_rules?languages=$1&targetKey=$2&tags=$3" 2>/dev/null)
}

### validate configuration parameters
function validate_parameters() {
  if [ -z "$TAG_ECOCONCEPTION" ]; then
    echo -e "ERROR - ${RED}config parameter 'TAG_ECOCONCEPTION' is NOT valid${NC} : it can't be empty"
    exit 1
  fi
  if [ -z "$SONAR_TOKEN" ]; then
    echo -e "ERROR - ${RED}config parameter 'SONAR_TOKEN' is NOT valid${NC} : it can't be empty"
    exit 1
  fi
  if [ -z "$SONAR_URL" ]; then
    echo -e "ERROR - ${RED}config parameter 'SONAR_URL' is NOT valid${NC} : it can't be empty"
    exit 1
  fi
  if [ -z "$RULES_KEYS" ]; then
    echo -e "ERROR - ${RED}config parameter 'RULES_KEYS' is NOT valid${NC} : it can't be empty"
    exit 1
  fi
}


###########################################################################################
#    C O R E     I N I T I A L I Z A T I O N    ( /!\ /!\ /!\ NOT to modify)
###########################################################################################

### transform string list of rules keys (separated by a ,) to an array
build_rules_array

### transform string list of profiles language keys (separated by a ,) to an array
build_profiles_language_array

### validate config parameters
validate_parameters