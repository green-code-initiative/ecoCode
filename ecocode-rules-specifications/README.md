# ecoCode rules specification repository

This project contains the specifications of all ecoCode rules, for all languages.

## Structure

Rules are organized by folder based on their ID in the [root rules folder](src/main/rules).
Each of these folders contains a file with the metadata of the rule, and description by language.

The metadata file uses the format supported by
the [SonarSource Analyzers Commons](https://github.com/SonarSource/sonar-analyzer-commons/tree/master/commons) library.
To find out what values can be put there, we advise you to use the
official [SonarQube documentation](https://docs.sonarsource.com/sonarqube/latest/user-guide/rules/overview/), and to
rely on already existing files.

Here is an example:

```text
src/main/rules
├── EC104
│   ├── java
│   │   ├── EC104.asciidoc
│   │   ├── EC104.json
│   ├── php
│   │   ├── EC104.asciidoc
│   ├── python
│   │   ├── EC104.asciidoc
│   └── EC104.json
├── ...
```

To specify metadata for a given language (for example deprecate a rule only for a single language), it is possible to
create a json file in the language folder, and this will be merged with the common file during build. The keys in the
specific file have priority and it is possible to add new ones but not to delete them from the global one.

## Description language

The description of the rules uses the ASCIIDOC format (
with [Markdown compatibility](https://docs.asciidoctor.org/asciidoc/latest/syntax-quick-reference/#markdown-compatibility))
in order to allow the inclusion of other pages (this feature is not available in standard with Markdown).

See:

* [AsciiDoc Syntax Quick Reference](https://docs.asciidoctor.org/asciidoc/latest/syntax-quick-reference/)
* [Compare AsciiDoc to Markdown](https://docs.asciidoctor.org/asciidoc/latest/asciidoc-vs-markdown/)
