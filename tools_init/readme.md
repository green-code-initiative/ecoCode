Rules Tagging system and Creating Quality Profile
=================================================

Purpose
-------
1. **Rules Tagging system**

Add one new tag to a list of rules (using SonarQube API).
Why ? because maybe some original SonarQube rules are already ready for being part of this plugin

2. **EcoCode Quality Profile**

Add one new Profile by language forked from SonarWay (using SonarQube API).
Why ? To use new rules eco-conception from ecocode plugins you will have to create a custom Profile.
The script attach the new rules with the new quality profile.

Requirements
------------

- Sonar installed
  - add new token in personal account settings to communicate with Sonar API
- Check `_config.sh` file :
  - debug mode (`DEBUG` variable) : 0 to disable, 1 to enable
  - simulation mode (`SIMULATION` variable) : 0 to disable, 1 to enable
  - Sonar token (`SONAR_TOKEN` variable) : put here the new added token previously
  - Sonar URL (`SONAR_URL` variable) : put here your custom Sonar URL ("http://localhost:9000" by default)
  - name of tag to add (`TAG_ECOCONCEPTION` variable) : the name of the new tag to add to a list of rules
  - rules keys list (string format separated with one comma) (`RULES_KEYS` variable) : specify here the list of all keys of rules that you want to add th new tag
  - name of profile to add (`PROFILE_ECOCONCEPTION` variable) : the name of the new profile to add for each language
  - language keys list (string format separated with one comma) (`PROFILES_LANGUAGE_KEYS` variable) : specify here the list of all keys language that you want to add the new ecocode quality profile

Development Environment
-----------------------
Differents environment have been used:
- bash 3.2 on MacOS
- Ubuntu 20.04.4 LTS (bash 5.0.17(1)-release) with extra jq package (`sudo apt install jq`)

Concepts
--------

Call Sonar API rest to
----------------------
1. **Tags**
- ... get rule data (included systags array and tags array)
- ... update rule data i.e tags array

2. **Quality Profile**
- ... create custom quality profile
- ... change parent profile
- ... get profile key
- ... add new rules to profile

Tags modifications
------------------

systags are not editable from api call (it seems to be nativally fullfilled with SonarQube installation)
tags array seems to be the editable part. It's editable from :

- Sonar UI usage
- Sonar API calls

Algorithm
---------
1. **Tags**

- get rules data (rules list from config file)
- check if new tag to add (from config file) already exists on systags array or tags array
- add new tag to all existing tags if necessary

2. **Profile**

- create a custom profile for each language
- change parent profiles with "Sonar Way"
- add new rules tag by `TAG_ECOCONCEPTION` variable to the custom profile

Scripts
-------

- check_tags.sh : read tags for all listed rules
- clean_tag.sh : delete specified tag from all listed rules
- install_tag.sh : add specified tag to all listed rules
- install_profile.sh : create new custom profile

How does it work ?
------------------

- change configuration in `_config.sh` file : check requirements above
- launch `check_tags.sh` to control your rules and tags
- launch `install_tags.sh` to add custom tag to your rules
- launch `check_tags.sh` again to control your rules and tags
- launch `install_profile.sh` to create profiles with the new rules from plugins