![Logo](docs/resources/logo-large.png)
======================================

_ecoCode_ is a collective project aiming to reduce environmental footprint of software at the code level. The goal of
the project is to provide a list of static code analyzers to highlight code structures that may have a negative
ecological impact: energy and resources over-consumption, "fatware", shortening terminals' lifespan, etc.

_ecoCode_ is based on evolving catalogs of [good practices](docs/rules), for various technologies. A SonarQube plugin
then implements these catalogs as rules for scanning your projects.

**Warning**: this is still a very early stage project. Any feedback or contribution will be highly appreciated. Please
refer to the contribution section.

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](https://github.com/green-code-initiative/ecoCode-common/blob/main/doc/CODE_OF_CONDUCT.md)

🌿 SonarQube Plugins
-------------------

6 technologies are supported by ecoCode right now:

- "standard" plugins :
  - [Java plugin](https://github.com/green-code-initiative/ecoCode-java)
  - [JavaScript plugin](https://github.com/green-code-initiative/ecoCode-javascript)
  - [PHP plugin](https://github.com/green-code-initiative/ecoCode-php)
  - [Python plugin](https://github.com/green-code-initiative/ecoCode-python)
  - [C# plugin](https://github.com/green-code-initiative/ecoCode-csharp)
- mobile plugins :
  - [Android plugin](https://github.com/green-code-initiative/ecoCode-android)
  - [iOS plugin](https://github.com/green-code-initiative/ecoCode-ios)

![Screenshot](docs/resources/screenshot.PNG)

### eco-design SonarQube plugin

![Ekko logo](docs/resources/5ekko.png)

There are two kinds of plugins :

- One for web / backoffice (PHP, Python, Java, JavaScript), using smells described in the 2nd edition of the repository
  published in september 2015.
  You can find all the
  rules [here (in french)](https://docs.google.com/spreadsheets/d/1nujR4EnajnR0NSXjvBW3GytOopDyTfvl3eTk2XGLh5Y/edit#gid=1386834576).
  The current repository is for web / backOffice
- One for mobile (Android), using [a set of smells](https://olegoaer.perso.univ-pau.fr/android-energy-smells/) theorised
  by Olivier Le Goaër for Android.
  You can find this plugin in the repository [here](https://github.com/green-code-initiative/ecocode-mobile)

### How a SonarQube plugin works

Code is parsed to be transformed as AST. AST will allow you to access one or more nodes of your code.
For example, you’ll be able to access of all your `for` loop, to explore content etc.

To better understand AST structure, you can use the [AST Explorer](https://astexplorer.net/).

🚀 Getting Started
------------------

You can quickly have a look of ecoCode plugins with docker. Plase have a look at "Getting started" section of each plugin :

- [Java plugin](https://github.com/green-code-initiative/ecoCode-java?tab=readme-ov-file#-getting-started)
- [PHP plugin](https://github.com/green-code-initiative/ecoCode-php?tab=readme-ov-file#-getting-started)
- [Python plugin](https://github.com/green-code-initiative/ecoCode-python?tab=readme-ov-file#-getting-started)
- [C# plugin](https://github.com/green-code-initiative/ecoCode-csharp?tab=readme-ov-file#-getting-started)

🛒 Distribution
------------------

The main way to get ecoCode plugins is to download them from your SonarQube Marketplace (available in Administration section).
But if you want, you can also download them from GitHub releases.

We had split our plugins repository `ecocode` to one repository for each plugin on december 2023.
Thus, plugin versions are available on 2 repositories depending on version you want :

- Java plugin :
  - from 0.x to 1.4.3 : [ecocode repository](https://github.com/green-code-initiative/ecoCode/releases)
  - since 1.5.0 : [ecoCode-java repository](https://github.com/green-code-initiative/ecoCode-java/releases)
- PHP plugin :
  - from 0.x to 1.3.1 : [ecocode repository](https://github.com/green-code-initiative/ecoCode/releases)
  - since 1.4.0 : [ecoCode-php repository](https://github.com/green-code-initiative/ecoCode-php/releases)
- Python plugin :
  - from 0.x to 1.3.1 : [ecocode repository](https://github.com/green-code-initiative/ecoCode/releases)
  - since 1.4.0 : [ecoCode-python repository](https://github.com/green-code-initiative/ecoCode-python/releases)
- Javascript plugin :
  - from 0.x to 1.3.0 : [ecocode repository](https://github.com/green-code-initiative/ecoCode/releases)
  - since 1.4.0 : [ecoCode-javascript repository](https://github.com/green-code-initiative/ecoCode-javascript/releases)
- C# plugin :
  - since 0.x : [ecocode repository](https://github.com/green-code-initiative/ecoCode-csharp/releases)
- Android plugin : [ecoCode-android repository](https://github.com/green-code-initiative/ecoCode-android/releases)
- iOS plugin : [ecoCode-ios repository](https://github.com/green-code-initiative/ecoCode-ios/releases)

🧩 Plugins version compatibility
------------------

| Plugins Version  | SonarQube version           |
|------------------|-----------------------------|
| 1.4.+            | SonarQube 9.4.+ LTS to 10.1 |
| 1.3.+            | SonarQube 9.4.+ LTS to 10.0 |
| 1.2.+            | SonarQube 9.4.+ LTS to 10.0 |
| 1.1.+            | SonarQube 9.4.+ LTS to 9.9  |
| 1.0.+            | SonarQube 9.4.+ LTS to 9.9  |
| 0.2.+            | SonarQube 9.4.+ LTS to 9.9  |
| 0.1.+            | SonarQube 8.9.+ LTS to 9.3  |

☕ Plugin Java part compatibility
------------------

| Plugins Version  | Java version |
|------------------|--------------|
| 1.4.+            | 11 / 17      |
| 1.3.+            | 11 / 17      |
| 1.2.+            | 11 / 17      |
| 1.1.+            | 11 / 17      |
| 1.0.+            | 11 / 17      |
| 0.2.+            | 11 / 17      |
| 0.1.+            | 11 / 17      |

🤝 Contribution
---------------

You are a technical expert, a designer, a project manager, a CSR expert, an ecodesign expert...

You want to offer the help of your company, help us to organize, communicate on the project ?

You have ideas to submit to us ?

We are listening to you to make the project progress collectively, and maybe with you !

WE NEED YOU !

Here the [Starter pack](https://github.com/green-code-initiative/ecoCode-common/blob/main/doc/starter-pack.md)

🤓 Main contributors
--------------------

Any question ? We are here for you !
first, create an issue, please.
Then, if no answer, contact ...

- [Jules Delecour](https://www.linkedin.com/in/jules-delecour-498680118/)
- [Geoffrey Lalloué](https://github.com/glalloue)
- [Julien Hertout](https://www.linkedin.com/in/julien-hertout-b1175449/)
- [Justin Berque](https://www.linkedin.com/in/justin-berque-444412140)
- [Olivier Le Goaër](https://olegoaer.perso.univ-pau.fr)
- [Maxime DUBOIS](https://www.linkedin.com/in/maxime-dubois-%F0%9F%8C%B1-649a3a3/)
- [David DE CARVALHO](https://www.linkedin.com/in/david%E2%80%8E-de-carvalho-8b395284/)
- [Maxime MALGORN](https://www.linkedin.com/in/maximemalgorn/)
- [Vianney DE BELLABRE](https://www.linkedin.com/in/vianney-de-bellabre/)

🧐 Core Team Emeriti
--------------------

Here we honor some no-longer-active core team members who have made valuable contributions in the past.

- Gaël Pellevoizin
- [Nicolas Daviet](https://github.com/NicolasDaviet)
- [Mathilde Grapin](https://github.com/fkotd)

They have contributed to the success of ecoCode :

- [Davidson Consulting](https://www.davidson.fr/)
- [Orange Business](https://www.orange-business.com/)
- [Snapp'](https://www.snapp.fr/)
- [Université de Pau et des Pays de l'Adour (UPPA)](https://www.univ-pau.fr/)
- [Solocal](https://www.solocal.com/) / [PagesJaunes.fr](https://www.pagesjaunes.fr/)
- [Capgemini](https://www.capgemini.fr/)

They supported the project :

- [Région Nouvelle-Aquitaine](https://www.nouvelle-aquitaine.fr/)

Links
-----

- https://docs.sonarqube.org/latest/analysis/overview/
