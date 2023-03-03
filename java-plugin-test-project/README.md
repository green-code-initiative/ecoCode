Step 0 : requirements
---

launch local environment with tools :
- `/tool_build.sh`
- `/tool_start.sh` (if docker environment already built)
- `/tool_docker-init.sh` (if docker environment not built yet)
check https://localhost:9000
configure (if docker environment already built) :
- change password of admin user
- check if plugin is installed on "marketPlace" tab on Administration
- create a new profile on each language to test - extend from Sonar WAY
- make this new profile as default
- add all rules "eco-conception" tagged on this new profile

Step 1 : compile and build
---

`mvn clean compile`

Step 2 : Send metrics to local SonarQube
---

`mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar -Dsonar.login=admin -Dsonar.password=XXX`
