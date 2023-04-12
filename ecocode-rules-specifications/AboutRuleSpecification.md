# How to specify rules

### Title

The name of the rule. Must be very short (a few words)

### ID

The identifier of a rule is built like that:

- Starts with: `EC` (for _ecoCode_ rule)
- Then number

For example for the first leakage rule we have the id: `ELEA001`

## Severity / Remediation Cost

### Severity

The severity can be seen as the impact of a rule in its category. Is it just a detail or is it fundamental ?
Here is an array to help choosing the severity level:

| Severity |                                                                 |
|----------|-----------------------------------------------------------------|
| Critical | Can be dramatic if not fix                                      |
| Major    | Important impact                                                |
| Minor    | Low impact                                                      |
| Info     | Not really a problem but needed to be say                       |
| Helpful  | Doing this will help to have a more eco-responsible application |

### Remediation cost

This is the evaluation of the cost of the remediation of the issue in a project.

| Remediation Cost |                                                                                                                          |
|------------------|--------------------------------------------------------------------------------------------------------------------------|
| None             | No cost - mainly for helpful rules                                                                                       |
| Trivial          | No need to understand the logic and no potential impact (removing import, logs...)                                       |
| Easy             | No need to understand the logic but potential impacts                                                                    |
| Medium           | Understanding the logic of a piece of code is required before doing a little and easy refactoring (1 or 2 lines of code) |
| Major            | Understanding the logic of a piece of code is required and it's up to the developer to define the remediation action     |
| High             | The remediation action might lead to locally impact the design of the application.                                       |
| Complex          | The remediation action might lead to an impact on the overall design of the application                                  |

## Rule short description

The short description is a small sentence that quickly explain the impact of the rule to the user.
It must be short and should directly engage the user.

eg.
> - Failing to call `release()` on a Media Player may lead to continuous battery consumption.
> - Forcing brightness to max value may cause useless energy consumption.

## Rule complete description

### Text

The long description of the rule. It must explain the whole rule characteristics using the current language.

### HTML

The HTML version of the rule description. It may contain a Non-compliant and compliant code example.
Here is a template of the HTML description of a rule:

```html
<p>My long rule description with some <code>code</code> in it.</p>
<h2>Non-compliant Code Example</h2>
<pre>
MyCode code = new DirtCode();
</pre>
<h2>Compliant Solution</h2>
<pre>
MyCode code = new GoodCode()
</pre>
```

## Implementation principle

A description in current language on how to implement the rule based on an AST of the code.

## Additional information

* [Coding rule guidelines - SonarQube Docs](https://docs.sonarqube.org/latest/extension-guide/adding-coding-rules/#coding-rule-guidelines)
