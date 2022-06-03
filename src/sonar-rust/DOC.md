# Documentation
## Requirements

- An existing instance of SonarQube server with minimum version v7.9
- Install SonarQube Scanner and be sure you can call `sonar-scanner` from your project root directory 
- You need network connectivity between the machine running the scan and the SonarQube server 

## Installation

1. Download the [latest plugin JAR file](https://github.com/elegoff/sonar-rust/releases) and copy it into the `extensions/plugins` folder of SonarQube
2. Restart your SonarQube server

## Upgrade 

Same as installing, except you will need to delete the previous plugin jar file from the `extensions/plugins` folder
(SonarQube would fail to start if detects two different versions of the same plugin)


## Generating Clippy report files :

This plugin will not detect any issue unless you provide a location of Clippy report JSON file(s)

Typically, a Clippy JSON report can be generated with

`cargo clippy --message-format=json`

where console output can be redirected into a file:

`cargo clippy --message-format=json &> <CLIPPY REPORT FILE>`

## Minimal Configuration the Rust Analysis 

- Add a file `sonar-project.properties` at the root of your project (More details in the [official documentation](https://docs.sonarqube.org/8.9/analysis/scan/sonarscanner/#header-1))

- add an extra SonarQube analysis parameter `community.rust.clippy.reportPaths` setting the Clippy report file(s) location

NB: `community.rust.clippy.reportPaths` supports passing of multiple clippy report files by comma separating them.

## Adding coverage measures 

Optionally SonarQube can also display coverage measures

This Community Rust plugin doesn't run your tests or generate coverage reports for you. That has to be done before analysis and provided in the form of reports.

Currently, two coverage formats are supported :

#### LCOV

Insert a parameter `community.rust.lcov.reportPaths` into you `sonar-project.properties` file.
The parameters sets the location of the LCOV report(s) (possibly a comma separated list of multiple LCOV reports)

#### COBERTURA

Insert a parameter `community.rust.cobertura.reportPaths` into you `sonar-project.properties` file.
The parameters set the location of the COBERTURA report(s) (possibly a comma separated list of multiple COBERTURA reports)

The Rust plugin only requires you provide reports that comply to one of those formats. It does not need to know or care which tools are used to generate the data

As an example, one of such coverage tool for Rust is [Tarpaulin](https://docs.rs/crate/cargo-tarpaulin/) which is 
>designed to be a code coverage reporting tool for the Cargo build system

It allows multiple coverage output formats 
e.g

`cargo tarpaulin --out Lcov` : for LCOV

`cargo tarpaulin --out Xml` : for Cobertura

But [other coverage tools](https://vladfilippov.com/blog/rust-code-coverage-tools/) might work as well

## Highlighting unit tests
By default, the plugin will highlight Rust unit tests for functions having attributes `#[test]` or `#[tokio::test]`
You may configure different attributes with parameter `community.rust.unitttests.attributes`


## Adding test measures

Optionally SonarQube can also display tests measures.

This Community Rust plugin doesn't run your tests or generate tests reports for you. That has to be done before analysis 
and provided in the form of reports.

Currently, only `junit report` formats are supported :

Insert a parameter `community.rust.test.reportPath` into your `sonar-project.properties` file. 
As an example, one of such tool for Rust that converts `cargo test` report to `junit report` is [cargo2junit](https://crates.io/crates/cargo2junit).

### Ignore duplications on unit tests

By default the unit tests are included as part as the duplication calculation

You may override the default `false` for the property `community.rust.cpd.ignoretests` either globally from the UI
(*Administration->Configuration->Rust*) or by setting `community.rust.cpd.ignoretests=true` in your `sonar-project.properties` file.