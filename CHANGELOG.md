# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- [#44](https://github.com/green-code-initiative/ecoCode/pull/44) Update the PHP description rules
- [#40](https://github.com/green-code-initiative/ecoCode/issues/40) (PR #54) Refactoring of package names (`cnumr` to  `greencodeinitiative`)
- modifying documentation to move a part to `ecoCode-common` repository
- move common init scripts to `ecoCode-common` repository

### Changed

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

### Added

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

- [161](https://github.com/cnumr/ecoCode/issues/161) adding tool to update tags of native rules with our custom tags

### Changed

- documentation upgrade (internal)
- optimization/refactoring on pom.xml dependencies (internal)

## [0.1.0] - 2022-12-14

### Added

- First official release of ecocode plugins : java plugin, php plugin and python plugin

[unreleased]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.2...HEAD

[0.2.2]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.1...v0.2.2

[0.2.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.2.0...v0.2.1

[0.2.0]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.1...v0.2.0

[0.1.1]: https://github.com/green-code-initiative/ecoCode/compare/v0.1.0...v0.1.1

[0.1.0]: https://github.com/green-code-initiative/ecoCode/releases/tag/v0.1.0