# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- [#216](https://github.com/green-code-initiative/ecoCode/issues/216) Upgrade rule EC2 for Java : Multiple if-else statement improvment
- [#106](https://github.com/green-code-initiative/ecoCode/issues/106) Upgrade RULES.md : rule EC67 not relevant neither for Python nor Rust

### Deleted

## [1.4.0] - 2023-08-08

### Added

- [#205](https://github.com/green-code-initiative/ecoCode/issues/205) compatibility with SonarQube 10.1
- [#210](https://github.com/green-code-initiative/ecoCode/pull/210) Publish to Maven Central (module ecocode-rules-specifications)

### Deleted

- [#182](https://github.com/green-code-initiative/ecoCode/issues/182) Split repository : move Python module to new `ecoCode-python` repository
- [#182](https://github.com/green-code-initiative/ecoCode/issues/182) Split repository : move Php module to new `ecoCode-php` repository

## [1.3.1] - 2023-07-19

### Added

- [#207](https://github.com/green-code-initiative/ecoCode/issues/207) Add release tag analyzis on SonarCloud

### Changed

### Deleted

- [#211](https://github.com/green-code-initiative/ecoCode/pull/211) Move JavaScript plugin to its dedicated repository

## [1.3.0] - 2023-07-04

### Added

- [#108](https://github.com/green-code-initiative/ecoCode/issues/108) new Python rule EC66 : Use single quote (') instead of quotation mark (")
- [#109](https://github.com/green-code-initiative/ecoCode/issues/109) new PHP rule EC3 : Getting the size of the collection in the loop. For further [RULES.md](./RULES.md) file
- [#113](https://github.com/green-code-initiative/ecoCode/issues/113) new Python rule EC10 : Use unoptimized vector images
- [#127](https://github.com/green-code-initiative/ecoCode/issues/127) new Python rule EC404 : Usage of generator comprehension instead of list comprehension in for loop declaration
- [#190](https://github.com/green-code-initiative/ecoCode/pull/190) Add Python rule: Use unoptimized vector images
- [#191](https://github.com/green-code-initiative/ecoCode/issues/191) Update rule tags for Java, Python, and PHP plugins
- [#192](https://github.com/green-code-initiative/ecoCode/pull/192) new Python rule EC203 : Detect unoptimized file formats
- Add JavaScript rules from [ecoCode ESLint plugin v0.2.0](https://github.com/green-code-initiative/ecoCode-linter/releases/tag/eslint-plugin%2F0.2.0)

### Changed

- [#19](https://github.com/green-code-initiative/ecoCode-common/issues/19) process changed for development environment installation : easier to initialize locally environment (check [`INSTALL.md`](https://github.com/green-code-initiative/ecoCode-common/blob/main/doc/INSTALL.md#howto-install-sonarqube-dev-environment) file)
- [#187](https://github.com/green-code-initiative/ecoCode/issues/187) upgrade librairies to SonarQube 10.0.0
- [#196](https://github.com/green-code-initiative/ecoCode/issues/196) updating PHP files to make them following the coding standards (PSR-12)
- [#201](https://github.com/green-code-initiative/ecoCode/pull/201) Clean-up plugins and dependencies
- technical : upgrade of maven plugins versions

## [1.2.1] - 2023-04-18

### Changed

- [#180](https://github.com/green-code-initiative/ecoCode/pull/180) correction of SonarQube review for MarketPlace (sonar plugin)

## [1.2.0] - 2023-04-14

### Added

- [#171](https://github.com/green-code-initiative/ecoCode/issues/171) Add migration mechanism to support "issue re-keying"

### Changed

- [#161](https://github.com/green-code-initiative/ecoCode/pull/161) Remove unnecessary junit dependencies in pom.xml
- [#166](https://github.com/green-code-initiative/ecoCode/issues/166) Correction of wrong message of rule EC63
- [#167](https://github.com/green-code-initiative/ecoCode/issues/167) Use same kind for rules across different languages
- [#173](https://github.com/green-code-initiative/ecoCode/issues/173) Update issue description of rule EC34 (try-catch)

## [1.1.0] - 2023-04-03

### Changed

- [#63](https://github.com/green-code-initiative/ecoCode/issues/63) Update plugins to be compliant for SonarQube MarketPlace integration ( PR [#79](https://github.com/green-code-initiative/ecoCode/pull/79) )
- [#88](https://github.com/green-code-initiative/ecoCode/pull/88) upgrade rules matrix with new ids + refactoring rules documentation (`RULES.md`)

### Deleted

- [#85](https://github.com/green-code-initiative/ecoCode/issues/85) Cleaning some useless classes on PHP plugin

## [1.0.0] - 2023-03-24

### Added

- [#44](https://github.com/green-code-initiative/ecoCode/pull/44) Update the PHP description rules
- [#67](https://github.com/green-code-initiative/ecoCode/pull/67) Add JavaScript plugin
- add `ecocode` tag on all rules on Java, PHP, Python and javascript plugins

### Changed

- [#40](https://github.com/green-code-initiative/ecoCode/issues/40) Refactoring of package names (`cnumr` to  `greencodeinitiative`)
- [#55](https://github.com/green-code-initiative/ecoCode/issues/55) rename `eco-conception` tag of rules to `eco-design`
- [#58](https://github.com/green-code-initiative/ecoCode/issues/58) check and upgrade compatibility to SonarQube 9.9
- move common init scripts to `ecoCode-common` repository
- modifying documentation and move `CONTRIBUTING.md`, `CODE_STYLE.md` and `INSTALL.md` to common doc in `ecoCode-common` repository
- security / performance optimizations : correction of `sonarcloud.io` security hotspots (java / php, python) and move Pattern compilation to static attribute
- [#64](https://github.com/green-code-initiative/ecoCode/issues/64) Python: ecoCode plugin with SonarQube, no code-smell detection
- [#65](https://github.com/green-code-initiative/ecoCode/issues/65) Create a test project to check new plugin rule in real environment
- [#71](https://github.com/green-code-initiative/ecoCode/issues/71) After an PHP analysis, no ecocode code smells appears in my Sonar project
- [#76](https://github.com/green-code-initiative/ecoCode/issues/76) correction of SonarQube plugins homepage link broken
- documentation upgrade

### Deleted

- cleaning old files (move them to `ecoCode-archive` repository)

## [0.2.2] - 2023-01-19

### Added

- [#23](https://github.com/green-code-initiative/ecoCode/pull/23) Add images to the description files.
- [#46](https://github.com/green-code-initiative/ecoCode/pull/46) Add CONTRIBUTING.MD, CODE_OF_CONDUCT.md and CODE_STYLE.md

### Changed

- [#27](https://github.com/green-code-initiative/ecoCode/pull/27) Fix [WARNING] Maven-shade-plugin overlapping classes and upgrade SonarRuntime.
- [#33](https://github.com/green-code-initiative/ecoCode/issues/33) Update plugin description in code
- [#42](https://github.com/green-code-initiative/ecoCode/issues/42) Fix Crash SonarQube analysis because of some ClassCast Exceptions
- [#48](https://github.com/green-code-initiative/ecoCode/pull/48) correction SONAR issue info - delete public keyword on tests
- Improve "build" GitHub actions to execute checks on branches from fork repositories

## [0.2.1] - 2022-12-30

### Changed

- [#22](https://github.com/green-code-initiative/ecoCode/issues/22) Error when running sonar scan with ecocode

## [0.2.0] - 2022-12-28

### Added

- [#15](https://github.com/green-code-initiative/ecoCode/pull/15) Upgrade some versions + sonar version from 9.3 to 9.8
- [#17](https://github.com/green-code-initiative/ecoCode/issues/17) improve releasing system
- [#25](https://github.com/green-code-initiative/ecoCode/issues/25) Release management vs maven packaging (not the same
  version)

### Changed

- documentation upgrade (internal)

## [0.1.1] - 2022-12-20

### Added

- [#161](https://github.com/cnumr/ecoCode/issues/161) adding tool to update tags of native rules with our custom tags

### Changed

- documentation upgrade (internal)
- optimization/refactoring on pom.xml dependencies (internal)

## [0.1.0] - 2022-12-14

### Added

- First official release of ecocode plugins : java plugin, php plugin and python plugin

[unreleased]: https://github.com/green-code-initiative/ecoCode/compare/v1.4.0...HEAD

[1.4.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.3.1...v1.4.0

[1.3.1]: https://github.com/green-code-initiative/ecoCode/compare/v1.3.0...v1.3.1

[1.3.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.2.1...v1.3.0

[1.2.1]: https://github.com/green-code-initiative/ecoCode/compare/v1.2.0...v1.2.1

[1.2.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.1.0...v1.2.0

[1.1.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.0.0...v1.1.0

[1.0.0]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.2...v1.0.0

[0.2.2]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.1...v0.2.2

[0.2.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.0...v0.2.1

[0.2.0]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.1...v0.2.0

[0.1.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.0...v0.1.1

[0.1.0]: https://github.com/green-code-initiative/ecoCode/releases/tag/v0.1.0
