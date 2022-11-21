# Basics

In order to develop a Sonarqube Plugin in Open source for ecocode, two basics must have been mastered: 
* How to develop a Sonarqube plugin 
* Understand and work withe the Gitflow

### Sonarqube Plugin

https://docs.sonarqube.org/latest/extend/developing-plugin/

### Gitflow

https://medium.com/android-news/gitflow-with-github-c675aa4f606a

### Github CNUMR Ecocode

https://github.com/cnumr/ecoCode/tree/main

### 115 green rules details

https://collectif.greenit.fr/ecoconception-web/115-bonnes-pratiques-eco-conception_web.html

If you don't understand what a rule means, take a look at the link above. Mouse over in order to have more details.

### 40 android rules details

https://olegoaer.perso.univ-pau.fr/android-energy-smells/

# Local development

### Prerequesites

You will need to install Docker : https://docs.docker.com/get-docker/

Docker-compose 3.9 : https://docs.docker.com/compose/install/

Java >=11 for Sonarqube plugin Development : https://www.java.com/fr/download/manual.jsp

Maven 3 for Sonarqube plugin Development : https://maven.apache.org/download.cgi

Additionnaly, install Git : https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

### Clone the project

Clone the project with :

```
git clone https://github.com/cnumr/ecoCode.git
```

### Start local environment

You will find all the steps to start a Sonarqube dev Environment here : https://github.com/cnumr/ecoCode/blob/main/src/INSTALL.md

### Choose your rule

Chose a rule in a specific language in the "To do" column : https://github.com/cnumr/ecoCode/projects/1 and move it to the "In progress" 

### Test your development

Each rule needs to have scripts in a specific language (i.e. Python, Rust, JS, PHP and JAVA) in order to test directly inside Sonarqube that the rule has been implemented.

To validate that the rule has been implemented, you need to execute a scan on those scripts. You will need sonar scanner: https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/


# Publish your work

### Commit your code 

Create a new branch following this pattern : <rule_id>-<language>
Example : 
```
git checkout -b 47-JS
```

Commit your code : 
```
git add .
git commit -m "your comments"
```

Push your branch :
```
git push origin <rule_id>-<language>
```

You may have to log with your account : https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token

### Open pull request

Once your code is pushed and tested, open a PR between your branch and "main" : https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request

### Review others development

Ask to people to review your PR. Once two people, at least, have reviewed, you can validate your PR
If you want to be reviewed, review others... It's a win/win situation

### Validation of a PR

Validate your PR or ask to someone who have the permissions to validate your PR
  
### Close your rule
  
Once your PR is validated, your rule integrates ecoCode. In https://github.com/cnumr/ecoCode/projects/1, move it from the "In Progress" column to the "Done" column. Well done.
