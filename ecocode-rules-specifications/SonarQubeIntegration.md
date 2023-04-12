\newpage

# Integrating a rule in SonarQube

This document explain what to do to implement a rule from the specification in SonarQube,
where to use each specification elements and the naming conventions.

*Emphasized* elements refer to elements described in [How to specify rules](AboutRuleSpecification.md#how-to-specify-rules).

## Java

### Rule class

- Package: `io.ecocode.*language*.checks`
- Class Name: `*Title*Rule`
- `@Rule` Annotations: `@Rule(key = "ECxx")` (where `xx` are digits)

### Test class

- `Package`: same as class but in `src/test/java` folder
- `Class Name`: `*Title*RuleTest`

### Rule description files (HTML and JSON)

- **HTML**
  - `Name`: *ID*_*language*.html -> **Sonar norm, will not work without that**
  - `Content`: use the *HTML version of the description of the rule*
- **JSON**
  - `Name`: *ID*_*language*.json -> **Sonar norm, will not work without that**
  - `Content`:
    - `Title`: *Subcategory*: *Title* (*Variant*)
    - `Type`: CODE_SMELL
    - `Status`: ready
    - `Remediation`
      - `func`: Constant/Issue
      - `constantCost`: see following [remediation cost conversion array](#remediation-cost-conversion-array)
    - `Tags`: *Category*, *Subcategory*, ecocode
    - `Default Severity`: *Severity* (_Helpful_ must be set to _Info_)


## Remediation cost conversion array

| Specification remediation | Trivial | Easy  | Medium | Major | High | Complex |
|---------------------------|---------|-------|--------|-------|------|---------|
| SonarQube constant        | 5min    | 10min | 20min  | 1h    | 3h   | 1d      |
