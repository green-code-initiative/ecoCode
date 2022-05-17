### ** Theory **

In order to develop a Sonarqube Plugin in Open source for Ecocode, two basics must have been mastered : 
* How to develop a sonarqube plugin 
* Understand and work withe the Gitflow

# Plugin basics : 

https://docs.sonarqube.org/latest/extend/developing-plugin/

# Gitflow :

https://medium.com/android-news/gitflow-with-github-c675aa4f606a

# Github CNUMR Ecocode :

https://github.com/cnumr/ecoCode/tree/main


### ** Local development **

# Prerequesites

You will need to install Docker : https://docs.docker.com/get-docker/
Docker-compose 3.9 : https://docs.docker.com/compose/install/
Java >=8 for Sonarqube plugin Development : https://www.java.com/fr/download/manual.jsp
Maven 3 for Sonarqube plugin Development : https://maven.apache.org/download.cgi

Additionnaly install Git : https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

# Clone the project

Clone the project with :

```
git clone https://github.com/cnumr/ecoCode.git
```

# Start local environment :

You will find all the steps to start a Sonarqube dev Environment here : https://github.com/cnumr/ecoCode/blob/main/src/INSTALL.md

# Chose your rule

Chose a rule in a specific language in the "To do" column : https://github.com/cnumr/ecoCode/projects/1 and move it to the "In progress" 

# Test your development

Each rule needs to have scripts in a specific language (ex : Python, Rust, JS, PHP and JAVA) in order to test directly inside Sonarqube that the rule has been implemented
To validate that the rule has been implemented, you need to execute a scan on those scripts. You will need sonar scanner : https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/


## ** Publish your work ***

# Open pull request
# Review others development
# Validation of a PR
