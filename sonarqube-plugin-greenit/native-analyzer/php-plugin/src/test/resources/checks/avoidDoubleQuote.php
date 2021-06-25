<?php
  $prenom = 'Hugo';
  $nom = "Hamon"; // NOK
  $age = 19;
  $estEtudiant = true;
  $cours = array('physique','chimie','informatique','philosophie');
  $unEtudiant = new Etudiant();


  $prenom = 'Hugo';
  $age = 19;

  echo $prenom;
  echo '<br/>';
  echo $age;

  $prenom = 'Hadrien';
  $age = 18;

  echo $prenom;
  echo "<br/>";// NOK
  echo $age;

  $identite = $prenom .' '. $nom;
  echo $identite;

  myFunction($nom, $age, $estEtudiant);

  myFunction("nom", "age", "estEtudiant"); // NOK

  myFunction('nom', 'age', 'estEtudiant');

  myFunction("nom", 'age', "estEtudiant"); // NOK

?>