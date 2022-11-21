### **💻 #GreenCode Work-package:**

The objective of this work-package is to develop rules that analyze the ecological impact of the source code and thus of the resulting **web application**.
Each rule must be implemented for each target language to be available in SonarQube plugin.
Several challenges are to be expected for this work-package. Each challenge corresponds to a **language/development environment**.

- **Java** Challenge: Develop rules specific to Java language based on the work already provided by ecoCode.
- **Php** Challenge: Develop rules specific to PHP language based on the work already provided by ecoCode.
- **Javascript** Challenge: Develop rules specific to Javascript language based on the work already provided by ecoCode.
- **Python** Challenge: Develop rules specific to Python language based on the work already provided by ecoCode.
- **Rust** Challenge: Develop specific rules for Rust development environment.

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

### **📱 #GreenAndroid Work-package :**

The objective of this work-package is to develop rules that analyze the ecological impact of the source code of an **Android** mobile application.

- **Android Code** Challenge: Develop Android specific rules based on the work already provided by ecoCode.
- **Android Test** Challenge: This challenge is oriented around reflection to find ways to scientifically measure the verification of ecodesign rules.

If you are a mobile developer, you can contribute to our [Android plugin](../sonarqube-plugin-greenit/android-plugin/). Android-specific rules relies on a multi-scope scanning, including Java source files, Xml files and Gradle files.

Before submitting an Android-specific SonarQube custom rule, please take a look at our [naming conventions](https://doc.rules.ecocode.io/#how-to-specify-rules). Idealy, take also a look at the [helpers fonctions](https://github.com/cnumr/ecoCode/tree/main/sonarqube-plugin-greenit/android-plugin/src/main/java/io/ecocode/java/checks/helpers) we wrote to avoid doing things the hard way.

#### Environment

The set of rules comes from the detailed [Energy Smells catalog](https://olegoaer.perso.univ-pau.fr/android-energy-smells/). 3/4 of them have been already implemented in the plugin. Table of unimplemented rules below:

| # | **Rule Name**      |     **Scope**     |
|---|:----------------|:-------------:|
| ELEA001 | Everlasting Service        | Java |
| EBOT004 | Uncached Data Reception       | Java |
| ESOB003 | Dark UI      | Java, Xml |
| ESOB009 | Day Night Mode     | File System, Xml |
| ESOB014 | High Frame Rate | Java |
| EBAT001 | Service@Boot-time    | Java, Xml  |
| EREL004 | Same dependencies    | Gradle |
| EREL005 | Duplicate dependencies    | Gradle |
| EREL007 | Clear cache    | Java |
| EREL008 | Convert to WebP | File System |
| EREL009 | Shrink Resources    | Gradle |
| EREL010 | Disable Obfuscation    | Gradle |


#### Social

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

#### Contest

Some practices are extremely hard to detect statically, yet well-proven to have an impact on the device's battery. To illustrate, let's try with the 3 rules below:
- Infinite Scroll: detect if the display contains an infinite scrolling strategy (with lazy loading)
- Caching network calls: detect if the result of a network call are stored in a data structure, for its later reuse
- Memoization: refer to the results of this [research paper](https://greenlab.di.uminho.pt/wp-content/uploads/2016/06/CIbSE19_memoization.pdf)

#### Measures

You can try to measure the impact of implemented rules on real Android projects to help us to set their severity (minor, major, critical, blocker) and their estimated remediation costs. For that purpose, you can use the `PowDroid` tool : https://gitlab.com/powdroid/powdroid-cli


### **🧠 #GreenBrainstorming Work-package :**

The objective of this Work-package is think the future of ecoCode. Whether you are a developer, a product owner or graphic designer, your contribution is valuable.

#### Good Practices

You may contribute to the identification of new good practices for the reduction of the carbon footprint, even in an informal way. You can start from [Best practices for energy efficient software](https://wiki.cs.vu.nl/green_software/Best_practices_for_energy_efficient_software). You can also leverage from [GR491](https://gr491.isit-europe.org/en/). You are also encouraged to take into account accessibility concerns like [RGAA](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/)

Keep in mind that practices ought to be implemented as custom coding rules in a SonarQube Plugin. Langages currently targeted by custom rules are `COBOL`, `Java`, `PHP`, `Python`, `Xml`. But don't limit yourself to this list, things change quickly.

#### User-friendly Interface

Since the user interface of sonarQube can be modified, it is possible to imagine a whole new user experience. Carbon-coverage is the new code coverage!
