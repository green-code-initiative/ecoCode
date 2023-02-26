# Applicable rules to SonarQube

Listing of rules applicable to SonarQube tool and easily scannable on a source code.

## Conditions of the applicable rules

An applicable rule must follow these conditions:

  - It must be statically detectable
  - It must be easily scriptable and return a single response (boolean: TRUE/FALSE)

## Rules

Web-oriented rules are based on :

- Ecoconception Web / Les 115 bonnes pratiques" book:
(https://github.com/cnumr/best-practices)
- GR491, LE GUIDE DE RÉFÉRENCE DE CONCEPTION RESPONSABLE DE SERVICES NUMÉRIQUES (https://gr491.isit-europe.org/)
- Référentiel général d'écoconception de services numériques (RGESN) (https://ecoresponsable.numerique.gouv.fr/publications/referentiel-general-ecoconception/)

All rules listed above are not applicable on scanner tool.
Here is the selected rules used on ecoCode : 
- [List of applicable web rules](web-rules.md)

We try to apply these rules to each supported langage, so we maintain a matrix to follow current development :
- [Rules support matrix by techno](web-matrix.md)