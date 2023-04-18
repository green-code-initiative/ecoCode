# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

### Changed

### Deleted

## [1.2.1] - 2023-04-18

### Added

### Changed

- [#180](https://github.com/green-code-initiative/ecoCode/pull/180) correction of SonarQube review for MarketPlace (sonar plugin)

### Deleted

## [1.2.0] - 2023-04-14

### Added

- [#171](https://github.com/green-code-initiative/ecoCode/issues/171) Add migration mechanism to support "issue re-keying"

### Changed

- [#161](https://github.com/green-code-initiative/ecoCode/pull/161) Remove unnecessary junit dependencies in pom.xml
- [#166](https://github.com/green-code-initiative/ecoCode/issues/166) Correction of wrong message of rule EC63
- [#167](https://github.com/green-code-initiative/ecoCode/issues/167) Use same kind for rules across different languages
- [#173](https://github.com/green-code-initiative/ecoCode/issues/173) Update issue description of rule EC34 (try-catch)

### Deleted

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
- [#58](https://github.com/green-code-initiative/ecoCode/issues/58) check and upgrade compatibility to SonarQube 9.9
- move common init scripts to `ecoCode-common` repository
- modifying documentation and move `CONTRIBUTING.md`, `CODE_STYLE.md` and `INSTALL.md` to common doc in `ecoCode-common` repository
- security / performance optimizations : correction of `sonarcloud.io` security hotspots (java / php, python) and move Pattern compilation to static attribute
- [#65](https://github.com/green-code-initiative/ecoCode/issues/65) Create a test project to check new plugin rule in real environment
- [#71](https://github.com/green-code-initiative/ecoCode/issues/71) After an PHP analysis, no ecocode code smells appears in my Sonar project
- [#64](https://github.com/green-code-initiative/ecoCode/issues/64) Python: ecoCode plugin with SonarQube, no code-smell detection
- [#55](https://github.com/green-code-initiative/ecoCode/issues/55) rename `eco-conception` tag of rules to `eco-design`
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
- Improve "build" Github actions to execute checks on branches from fork repositories

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

[unreleased]: https://github.com/green-code-initiative/ecoCode/compare/v1.2.1...HEAD

[1.2.1]: https://github.com/green-code-initiative/ecoCode/compare/v1.2.0...v1.2.1

[1.2.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.1.0...v1.2.0

[1.1.0]: https://github.com/green-code-initiative/ecoCode/compare/v1.0.0...v1.1.0

[1.0.0]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.2...v1.0.0

[0.2.2]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.1...v0.2.2

[0.2.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.0...v0.2.1

[0.2.0]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.1...v0.2.0

[0.1.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.0...v0.1.1

[0.1.0]: https://github.com/green-code-initiative/ecoCode/releases/tag/v0.1.0
