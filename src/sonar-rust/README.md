|     |     |     
| --- | --- | 
|  [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=elegoff_sonar-rust&metric=alert_status)](https://sonarcloud.io/dashboard?id=elegoff_sonar-rust) | ![Coverage](https://sonarcloud.io/api/project_badges/measure?project=elegoff_sonar-rust&metric=coverage) |
| [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)|[Download latest release](https://github.com/elegoff/sonar-rust/releases) |

## SonarQube plugin for Rust (Community)

The plugin enables analysis of Rust language within [SonarQube](https://www.sonarqube.org), which is an open platform to manage code quality.
It is compatible with SonarQube 7.9 and above.

It leverages [Clippy lints](https://rust-lang.github.io/rust-clippy/master/) to raise issues against coding rules,  [LCOV](https://wiki.documentfoundation.org/Development/Lcov) or [Cobertura](http://cobertura.github.io/cobertura/) for code coverage.

### How ?

#### tl;dr

* Generate a Clippy report
  
`cargo clippy --message-format=json &> <CLIPPY REPORT FILE>`
* Import it into SonarQube
   
set analysis parameter `community.rust.clippy.reportPaths=<CLIPPY REPORT FILE>`

* Optionally import tests measures (`junit` report)

use `community.rust.test.reportPath`

* Optionally import coverage measures 

use either 

`community.rust.lcov.reportPaths` 

or 

`community.rust.cobertura.reportPaths`


For more details, you may want to read :
* The [documentation](./DOC.md)
* The [FAQ page](./FAQ.md)

***
*This plugin is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.*

Your contribution and/or user feedback is welcomed

*Contact :* <community-rust@pm.me>



