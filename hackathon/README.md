<p align="center">
  <img src="../docs/logo-large.png">
</p>
<h1 align="center">
  🎈 Challenge 🎈
</h1>

Help us improve ecoCode (powered by SonarQube) by contributing to various work packages (WPs).

<h2>WP#1 - General-Purpose Rules</h2>

<h2>WP#2 - Android-Specific Rules</h2>

If you are a mobile developer, you can contribute to our [Android plugin](../sonarqube-plugin-greenit/android-plugin/). Android-specific rules relies on a multi-scope scanning: Java source file, Xml file and now Gradle file.

<h3>⚡ Energy</h3>

This set or rules comes from the [Energy Smells catalog](https://olegoaer.perso.univ-pau.fr/android-energy-smells/). xx have already been implemented in the plugin.

<h3>♿ Social</h3>

This set or rules comes from the [Social Smells catalog](https://olegoaer.perso.univ-pau.fr/android-social-smells/index.html). 0 rules have been implemented so far in the plugin.

<h3>💪 Contest</h3>

Some practices are extremely hard to detect statically, yet well-proven to have an impact on the device's battery. To illustrate, let's try with the 3 rules below:
- Infinite Scroll
- Caching network calls
- Memoization of functions (see https://greenlab.di.uminho.pt/wp-content/uploads/2016/06/CIbSE19_memoization.pdf)

<h2>WP#3 - New Rules & Assessment</h2>

If you are not interested by implementing existing rules, you may constribute to the identification of new ones. Also, you can try to evaluate  the impact of rules on real projects to help us to set the severity of each (minor, major, blocking).
