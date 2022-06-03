#FAQ


* ### How do I build the plugin from source

This project is built with Maven
Use : `mvn clean package`

* ### Can I use this plugin on SonarCloud ?

SonarCloud is a little different to SonarQube in that it doesn’t currently provide the ability to add third-party plugins like `Sonar rust`.

* ### Can I use this plugin on SonarLint ?

Currently the `Community Rust` plugin is not supported by SonarLint.

* ### Do I need a SonarQube license to use this plugin ?

You don't. The (free) Community edition v7.9+ is compatible (and so are Commercial editions of SonarQube)

* ### Can I scan my Rust source code from a CI/CD pipeline ? Can I get Pull Requests decoration ?

There is nothing specific for this plugin. Any CI pipeline which allows a SonarQube analysis can trigger an analysis on a Rust project
The only requirement is that the `Community Rust` plugin is installed on your SonarQube instance

* ### Does this Community Rust plugin provide a built-in Quality Profile ?

Yes, but the current set of rules is very limited. Contributions for adding new rules are welcomed !

* ### My project is a mix of Rust and other programming languages, will this work ?

Yes, the issues on other languages will be detected by their respective language analyzers

* ### How do I reach out for issues, feature requests or questions not listed here ?

- Issues / Feature requests specific to this plugin can be tracked after you [create a Github issue](https://docs.github.com/en/issues/tracking-your-work-with-issues/creating-issues/creating-an-issue)
- Issues that are related to SonarQube behaviour should be reported in their [Community forum](https://community.sonarsource.com/)
- Private material (like source files failing to be parsed) can be sent at community-rust@pm.me


* ### How can I contribute to improving/fixing this plugin ?

We would love to have more contributors. 
Please read this nice [article](https://gist.github.com/MarcDiethelm/7303312) to get ideas how you can help






