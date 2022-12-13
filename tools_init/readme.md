Purpose
=======
Add one new tag to a list of rules (using SonarQube API).
Why ? because maybe some original SonarQube rules are already ready for being part of this plugin

Requirements
============

- Sonar installed
  - add new token in personal account settings to communicate with Sonar API
- Check `_config.sh` file :
  - debug mode (`DEBUG` variable) : 0 to disable, 1 to enable
  - simulation mode (`SIMULATION` variable) : 0 to disable, 1 to enable
  - Sonar token (`SONAR_TOKEN` variable) : put here the new added token previously
  - Sonar URL (`SONAR_URL` variable) : put here your custom Sonar URL ("http://localhost:9000" by default)
  - name of tag to add (`TAG_ECOCONCEPTION` variable) : the name of the new tag to add to a list of rules
  - rules keys list (string format separated with one comma) (`RULES_KEYS` variable) : specify here the list of all keys of rules that you want to add th new tag
  
Development Environment
=======================

bash 3.2 on MacOS

Concepts
========

call Sonar API rest to ...
--------------------------

- get rule data (included systags array and tags array)
- update rule data i.e tags array

tags modifications
------------------

systags are not editable from api call
(This array seems to be fullfilled with tags inside implemented rules)
tags array seems to be the tags part editable from :

- Sonar UI
- Sonar API calls

Algorythm
---------

- get rules data (rules list from config file)
- check if new tag to add (from config file) already exists on systags array or tags array
- add new tag to all existing tags if necessary

Scripts
-------

- check_tags.sh : read tags for all listed rules
- clean_tag.sh : delete specified tag from all listed rules
- install_tag.sh : add specified tag to all listed rules

How it works ?
==============
- change configuration in `_config.sh` file : check requirements above
- launch `check_tags.sh` to control your rules and tags
- launch `install_tags.sh` to add custom tag to your rules
- launch `check_tags.sh` again to control your rules and tags