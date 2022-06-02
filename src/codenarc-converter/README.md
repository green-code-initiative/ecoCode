# CodeNarc Implementation

## Objective

Being able to handle groovy files/projects to make this plugin more relevant.

## Why CodeNarc?

CodeNarc is a known groovy analyser, both effective and customizable, therefore its use matches our goal.

## How?

Thanks to [SonarQube implementation of Codenarc](https://github.com/Inform-Software/sonar-groovy) we managed to make our own implementation of Codenarc.
This implementation is far from perfect and still requires tweaking (e.g. simpler build process).

To summarize Codenarc implementation process:

- We create a custom Codenarc version that we build.
- We use that implementation as source for codenarc-converter.
- Codenarc-converter parses Codenarc output and generates Sonarqube rules using CodeNarc's documentation.
  Then it's used in ecoCode plugin profile to analyse groovy files.

### Step 1: configuring the rule in CodeNarc

Launch `[...]/codenarc-converter/CodeNarc/codenarc.groovy` script using:

```sh
groovy codenarc.groovy create-rule
```

You will be asked a few questions that will modify documentation files in `[...]/codenarc-converter/Codenarc`:

- `/CHANGELOG.md`: changelist.
- `/docs/codenarc-base-messages.properties`: rules description.
- `/docs/codenarc-rules-ecocode.md`: rules documentation.
- `/src/main/resources/rulesets/ecocode.xmlecocode.xml`: rules list.

and generates rule files:

- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>Rule.groovy`: rule file.
- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>RuleTest.groovy`: rule test file.

Then, you need to manually edit these files since the script doesn't do it on its own:

- `/docs/StarterRuleSet-AllRulesByCategory.groovy.txt`: Add your Rule to the list (order by category).
- `/docs/StarterRuleSet-AllRules.groovy.txt`: Add your Rule to the list (order by alphabetical order).

### Step 2: adding the rule in the Android plugin

Build codenarc-converter using:

```sh
cd [...]/codenarc-converter
mvn clean package 
```

Tests should fail at this point, you still need to modify a few files.

In `[...]/android-plugin/src/main/resources/org/sonar/plugins/groovy`:

- `rules.xml`: replace it by `rules.xml` generated in `[...]/codenarc-converter/target/results`
- `profile-default.txt`: add your rule full qualified name (e.g. org.codenarc.rule.ecocode.SupportedVersionRangeRule) to the list
- `cost.csv`: add your rule cost to the list (see following [remediation cost conversion array](#remediation-cost-conversion-array))

### Step 3: writing your rule

At this point you just have to implement your rule and its test in their files:

- `[...]/codenarc-converter/CodeNarc/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>Rule.groovy` for your rule code.
- `[...]/codenarc-converter/CodeNarc/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>RuleTest.groovy` for your rule tests.

### Step 4: building and checking implementation

Build Codenarc (gradle 6.9.2, Java 11):

```sh
cd [...]/codenarc-converter/CodeNarc
./gradlew build -x test 
```

Add custom CodeNarc to Maven dependencies:

```sh
cd [...]/codenarc-converter
mvn initialize
```

Build codenarc-converter:

```sh
cd [...]/codenarc-converter
mvn clean package
```

Build android-plugin:

```sh
cd [...]/android-plugin
mvn clean package
```

## Remediation cost conversion array

| Specification remediation | Trivial | Easy  | Medium | Major | High | Complex |
|---------------------------|---------|-------|--------|-------|------|---------|
| SonarQube constant        | 5min    | 10min | 20min  | 1h    | 3h   | 1d      |
