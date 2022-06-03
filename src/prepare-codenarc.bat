REM == Define CodeNarc version
set codenarc_version=2.2.2

REM == Build CodeNarc
cd codenarc-converter/CodeNarc

REM == Deploy to local repository
mvn install:install-file -Dfile=build/libs/CodeNarc-%codenarc_version%.jar -DgroupId=org.codenarc -DartifactId=CodeNarc -Dversion=%codenarc_version% -Dpackaging=jar
