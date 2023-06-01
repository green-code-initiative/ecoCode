<?php
  $lastName = 'Hugo';
  $name = "Hamon"; // NOK {{Avoid using double quote ("), prefer using simple quote (')}}
  $age = 19;
  $isStudent = true;
  $cours = array('physique','chimie','informatique','philosophie');

  echo $lastName;
  echo $name;
  echo '<br/>';
  echo "<br/>"; // NOK {{Avoid using double quote ("), prefer using simple quote (')}}
  echo $age;

  $identite = $lastName .' '. $name;
  echo $identite;

  myFunction($name, $age, $isStudent);

  myFunction("name", "age", "isStudent"); // NOK {{Avoid using double quote ("), prefer using simple quote (')}}

  myFunction('name', 'age', 'isStudent');

  myFunction("name", 'age', "isStudent"); // NOK {{Avoid using double quote ("), prefer using simple quote (')}}
