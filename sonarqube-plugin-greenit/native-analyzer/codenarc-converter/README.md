# CodeNarc Implementation

## Objective

Being able to handle groovy files/projects to make this plugin more relevant.

## Why CodeNarc?

CodeNarc is a know groovy analyser, both effective and customizable, therefor its use matches our goal.

## How?

Thx to [SonarQube implementation of Codenarc](https://github.com/Inform-Software/sonar-groovy) we managed to make our own implementation of Codenarc.
This implementation is far from perfect and still requires tweaking (e.g. simpler build process).

To summarize Codenarc implementation process:

- We create a custom Codenarc version that we build.
- We use that implementation as source for codenarc-converter.
- Codenarc-converter parses Codenarc output and generates Sonarqube rules using CodeNarc's documentation.
  Then it's used in ecoCode plugin profile to analyse groovy files.

### Step 1: configuring the rule in CodeNarc

Launch `/codenarc-converter/CodeNarc/codenarc.groovy` script using:

```sh
groovy codenarc.groovy create-rule
```

You will be asked a few questions that will modify documentation files in `/codenarc-converter/Codenarc`:

- `/CHANGELOG.md`: Changelist.
- `/docs/codenarc-base-messages.properties`: Rules Description.
- `/docs/codenarc-rules-ecocode.md`: Rules Documentation.
- `/src/main/resources/rulesets/ecocode.xmlecocode.xml`: Rules List.

and generates rule files:

- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>Rule.groovy`: Rule File.
- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>RuleTest.groovy`: Rule Test File.

After you need to manually edit these files since the script doesn't do it on its own:

- `/docs/StarterRuleSet-AllRulesByCategory.groovy.txt`: Add your Rule to the list (order by category).
- `/docs/StarterRuleSet-AllRules.groovy.txt`: Add your Rule to the list (order by alphabetical order).

### Step 2: adding the rule in the Android plugin

Build codenarc-converter using:

```sh
cd /sonarqube-plugin-greenit/native-analyzer/codenarc-converter
mvn clean package 
```

Test should fail at this point, you still need to modify a few files.

In `/android-plugin/src/main/resources/org/sonarorg/sonar/plugins/groovy`:

- `rules.xml`: Replace by rules.xml generated in codenarc-converter/target/results
- `profile-default.txt`: Add your Rule full qualified name (e.g. org.codenarc.rule.ecocode.SupportedVersionRangeRule) to the list.
- `cost.csv`: Add your Rule cost to the list (see following [remediation cost conversion array](#remediation-cost-conversion-array))

### Step 3: writing your rule

At this point you just have to implement your rule and its test in their files:

- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>Rule.groovy` for your rule code.
- `/src/main/groovy/org/codenarc/rule/<Category>/<RULENAME>RuleTest.groovy` for your rule tests.

### Step 4: building and checking implementation

Build Codenarc (gradle 6.5.1, Java 14):

```sh
gradle build -x test 
```

Add custom CodeNarc to Maven dependencies:

```sh
cd /codenarc-converter
mvn initialize
```

Build codenarc-converter:

```sh
cd /codenarc-converter
mvn clean package
```

Build android-plugin:

```sh
cd /android-plugin
mvn clean package
```

## Remediation cost conversion array

| Specification remediation | Trivial | Easy  | Medium | Major | High | Complex |
|---------------------------|---------|-------|--------|-------|------|---------|
| SonarQube constant        | 5min    | 10min | 20min  | 1h    | 3h   | 1d      |
