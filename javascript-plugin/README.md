# ecoCode JavaScript plugin

This plugin behaves differently from the others in the ecoCode project. Since version 8.9 of SonarQube, it is no longer
possible to use an AST to implement a
rule, [as explained here](https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/javascript-custom-rules).
In compliance with the SonarSource decision, the ecoCode project uses ESLint to implement the custom rules.

Thus, the plugin does not implement any rules. Its purpose is to import the result of the ESLint analysis of the
project made with the ecoCode linter, with the complete documentation of each rule. In this context, the rules are
considered by SonarQube as external: they do not appear in the list of rules but are reported as real rules during the
analysis ([click to learn more](https://docs.sonarqube.org/latest/analyzing-source-code/importing-external-issues/importing-third-party-issues/)).

🚀 Getting Started
------------------

The installation is not much more complicated than another ecoCode plugin. In addition to the Sonar plugin, you will
need to install the ESLint plugin in your JavaScript/TypeScript project to be analyzed:

- Install the SonarQube plugin as described in the [ecoCode README](../README.md#-getting-started).
- Install the ESLint plugin into your project as described
  in [ESLint project README](https://github.com/green-code-initiative/ecoCode-linter/blob/main/eslint-plugin/README.md#installation).\
  This guide also explains how to configure ESLint to import results into SonarQube.
