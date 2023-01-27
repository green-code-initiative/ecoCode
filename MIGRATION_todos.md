
# Migration TODOs

## TODOs

- check if `_TODO_CHECK_sonarqube-plugin-greenit` is used
- delete `_TO_DELETE_images` if images not used
- [IN PROGRESS] check if `xml-plugin` is useful because
  - module is not declared as module in `pom.xml`
  - there is already XML rules in android-plugin 
- check documentation
  - [DONE] rewrite and merge `README.md` and `README2.md`
  - in `docs` directory
- migrate https://github.com/cnumr/ecoLinter to a new repo (`ecoCode-linter`)
  - split JS from CSS
  - compare actual ecollinter-plugin in ecocode standard and ecocodelinter of external repo

## IDEAS

.. to tranform into issues ?

- [IN PROGRESS] check `pom.xml` dependencies (usefulness, scope, versions)
  - [DONE] first clean, check scopes, factorization
  - check usefulness
  - upgrade versions
- [IN PROGRESS] upgrade sonar version in docker-compose file (in standard and mobile plugin)
- enable github `dependabot` to create automatically PR with version upgrades of dependencides (when all dependencies will be ok)
