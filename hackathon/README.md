<p align="center">
  <img src="../docs/resources/logo-large.png">
</p>
<h1 align="center">
  🎈 Challenge 🎈
</h1>

Help us improve ecoCode (powered by SonarQube) by contributing to various work packages (WPs).

<h2>Prerequisites ⚒️</h2>

https://docs.sonarqube.org/latest/extend/developing-plugin/

<h2>WP#1 - Cross-Platform Rules 💻</h2>

Each rule must be implemented for each target language to be available in SonarQube plugin.

Here is the list of rules already available in ecoCode project code.

- ✅ Rule included in current version of ecoCode
- 🚫 Non applicable rule

| Title  | Java | Php | Javascript | Python | Rust | ... |
|--|--|--|--|--|--|--|
| Use official social media sharing buttons | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Include a CSS file containing directives not used on a page | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-grouped similar CSS declarations | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| CSS shorthand notations not used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| CSS print not included | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-standard fonts used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-outsourced CSS and Javascript | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Image tags containing an empty SRC attribute | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Resize images outside the browser | | ✅  | | ✅  | | |
| Use unoptimized vector images |  |  |  |  |  | |
| Using too many CSS/javascript animations | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Modify the DOM when traversing it | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Edit DOM elements to make it invisible | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Modify a CSS property directly | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Using try...catch...finally calls |  |  |  |  | | |
| The use of methods for basic operations |  |  |  |  | | |
| Call a DOM element multiple times without caching | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Use global variables |  |  |  |  |  |  | | |
| Using strings as arguments to SetTimeout() and setInterval() |  |  |  |  | | |
| Using for...in loops | ✅  |  |  |  | | |
| Rewrite native getter/setters |  |  |  |  | | |
| Unnecessarily assigning values to variables | ✅  |  |  |  | | |
| Use single quote (') instead of quotation mark (") | | ✅  | | ✅  | | |
| Use the $i++ variable during an iteration | ✅  | ✅  |  |  | | |
| Calling a function in the declaration of a for loop | ✅  | ✅  |  | ✅  | | |
| Perform an SQL query inside a loop | ✅  | ✅  |  |  | | |
| Write SELECT * FROM | ✅  | ✅  |  | ✅  | | |

<h2>WP#2 - Android-Specific Rules 📱</h2>

If you are a mobile developer, you can contribute to our [Android plugin](../src/android-plugin/). Android-specific rules relies on a multi-scope scanning, including Java source files, Xml files and Gradle files.

Before submitting an Android-specific SonarQube custom rule, please take a look at our [naming conventions](https://doc.rules.ecocode.io/#how-to-specify-rules). Idealy, take also a look at the [helpers fonctions](https://github.com/cnumr/ecoCode/tree/main/src/android-plugin/src/main/java/io/ecocode/java/checks/helpers) we wrote to avoid doing things the hard way.

<h3>Environment</h3>

The set of rules comes from the detailed [Energy Smells catalog](https://olegoaer.perso.univ-pau.fr/android-energy-smells/). 3/4 of them have been already implemented in the plugin. Table of unimplemented rules below:

| # | **Rule Name**      |     **Scope**     |
|---|:----------------|:-------------:|
| ELEA001 | Everlasting Service        | Java |
| EBOT004 | Uncached Data Reception       | Java |
| ESOB003 | Dark UI      | Java, Xml |
| ESOB009 | Day Night Mode     | File System, Xml |
| ESOB014 | High Frame Rate | Java |
| EBAT001 | Service@Boot-time    | Java, Xml  |
| EREL003 | Supported Version Range    |  Xml, Gradle |
| EREL004 | Same dependencies    | Gradle |
| EREL005 | Duplicate dependencies    | Gradle |
| EREL006 | Fat app    | Gradle |
| EREL007 | Clear cache    | Java |
| EREL008 | WebP image | File System |
| EREL009 | Shrink Resources    | Gradle |
| EREL010 | Disable Obfuscation    | Gradle |


<h3>Social</h3>

The set of rules comes from the detailed [Social Smells catalog](https://olegoaer.perso.univ-pau.fr/android-social-smells/index.html) (work in progress). 0 rules have been implemented so far in the plugin. Table of unimplemented rules below:

| # | **Rule Name**      |     **Scope**     |
| ---|:----------------|:-------------:|
| SPRI001 | Crashlytics automatic opt-in       | Java, Xml |
| SPRI002 | Google Tracker | Java |
| SPRI003 | Hidden Tracker Risk      | Gradle |
| SPRI004 | Tracking Id      | Java |
| SPRI005 | Explain Permission     | Java |
| SGDP001 | Google consent | Java |
| SINC001 | Aging devices   | Gradle  |

<h3>Contest</h3>

Some practices are extremely hard to detect statically, yet well-proven to have an impact on the device's battery. To illustrate, let's try with the 3 rules below:
- Infinite Scroll: detect if the display contains an infinite scrolling strategy (with lazy loading)
- Caching network calls: detect if the result of a network call are stored in a data structure, for its later reuse
- Memoization: refer to the results of this [research paper](https://greenlab.di.uminho.pt/wp-content/uploads/2016/06/CIbSE19_memoization.pdf)

<h3>Evaluation</h3>

You can try to measure the impact of implemented rules on real Android projects to help us to set their severity (minor, major, critical, blocker) and their estimated remediation costs. For that purpose, you can use the *PowDroid* tool : https://gitlab.com/powdroid/powdroid-cli

<h2>WP#3 - New Practices 💬</h2>

If you are not interested by implementing SonarQube rules, you may contribute to the identification of new good practices for the reduction of the carbon footprint, even in an informal way. You can start from [Best practices for energy efficient software](https://wiki.cs.vu.nl/green_software/Best_practices_for_energy_efficient_software) or leverage from your skills on specific technologies not supported yet.

**READY ? [Click here to launch a new discussion](https://github.com/cnumr/ecoCode/discussions/new?category=hackathon)**
