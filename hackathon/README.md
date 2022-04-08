<p align="center">
  <img src="../docs/logo-large.png">
</p>
<h1 align="center">
  🎈 Challenge 🎈
</h1>

Help us improve ecoCode (powered by SonarQube) by contributing to various work packages (WPs).

<h2>WP#1 - Cross-Platform Rules 💻</h2>

<h2>WP#2 - Android-Specific Rules 📱</h2>

If you are a mobile developer, you can contribute to our [Android plugin](../sonarqube-plugin-greenit/android-plugin/). Android-specific rules relies on a multi-scope scanning, including Java source files, Xml files and Gradle files.

Before submitting an Android-specific SonarQube custom rule , please take a look at our [naming conventions](https://doc.rules.ecocode.io/#how-to-specify-rules). Idealy, take also a look at the [helpers fonctions](https://github.com/cnumr/ecoCode/tree/main/sonarqube-plugin-greenit/android-plugin/src/main/java/io/ecocode/java/checks/helpers) we wrote to avoid doing things the hard way.

<h3>Environment</h3>

The set or rules comes from the detailed [Energy Smells catalog](https://olegoaer.perso.univ-pau.fr/android-energy-smells/). 3/4 have already been implemented in the plugin. Table of unimplemented rules below:

| **Rule Name**      |     **Scope**     |
|----------------|:-------------:|
| Everlasting Service        | Java |
| Uncached Data Reception       | Java |
| Dark UI      | Java, Xml |
| Day Night Mode     | File System, Xml |
| High Frame Rate | Java |
| Service@Boot-time    | Java, Xml  |
| Supported Version Range    |  Xml, Gradle |
| Same dependencies    | Gradle |
| Duplicate dependencies    | Gradle |
| Fat app    | Gradle |
| Clear cache    | Java |
| WebP image | File System |
| Shrink Resources    | Gradle |
| Disable Obfuscation    | Proguard |


<h3>Social</h3>

The set or rules comes from the detailed [Social Smells catalog](https://olegoaer.perso.univ-pau.fr/android-social-smells/index.html) (work in progress). 0 rules have been implemented so far in the plugin. Table of unimplemented rules below:

| **Rule Name**      |     **Scope**     |
|----------------|:-------------:|
| Crashlytics automatic opt-in       | Java, Xml |
| Hidden Tracker Risk      | Gradle |
| Tracking Id      | Java |
| Explain Permission     | Java |
| Google consent | Java |
| Aging devices   | Gradle  |

<h3>Contest</h3>

Some practices are extremely hard to detect statically, yet well-proven to have an impact on the device's battery. To illustrate, let's try with the 3 rules below:
- Infinite Scroll: detect if the display contains an infinite scrolling strategy (with lazy loading)
- Caching network calls: detect if the result of a network call are stored in a data structure, for its later reuse
- Memoization: refer to the results of this [research paper](https://greenlab.di.uminho.pt/wp-content/uploads/2016/06/CIbSE19_memoization.pdf)

<h2>WP#3 - New Practices / Rules Assessment 💬</h2>

If you are not interested by implementing SonarQube rules, you may contribute to the identification of new good practices, even in an informal way. Also, you can try to evaluate the impact of implemented rules on real projects to help us to set their severity (minor, major, blocking) and estimated remediation costs.
