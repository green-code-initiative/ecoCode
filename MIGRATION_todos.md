### TODOs

- mark as readonly the actual CNUMR repository and make communication about it
- check if `_TODO_CHECK_sonarqube-plugin-greenit` is used
- delete `_TO_DELETE_images` if images not used
- check if `xml-plugin` is useful because
  - module is not declared as module in `pom.xml`
  - there is already XML rules in android-plugin 
- check documentation
  - rewrite and merge `README.md` and `README2.md`
  - in `docs` directory
- migrate https://github.com/cnumr/ecoLinter to a new repo (`ecoCode-linter`)
  - split JS from CSS
  - compare actual ecollinter-plugin in ecocode standard and ecocodelinter of external repo
- [DONE] disable `ecoLinter` from ecocode standard

### IDEAS 
.. to tranform into issues ?

- release system (with versionning) to share released plugins
- check `pom.xml` dependencies (usefulness, scope, ...)
- upgrade sonar version in docker-compose file (in standard and mobile plugin)
