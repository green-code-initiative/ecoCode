package fr.greencodeinitiative.java.checks;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import java.util.*;

public class UseFetchTypeLazyRuleTest {

    @OneToMany(mappedBy = "firstEntity") // Noncompliant
    private Collection<SomeEntity> firstEntities;

    @ManyToMany(mappedBy = "firstEntity1") // Noncompliant
    private Collection<SomeEntity> firstEntities1;

    @OneToMany // Noncompliant
    private Collection<SomeEntity> secondEntities1;

    @ManyToMany // Noncompliant
    private Collection<SomeEntity> secondEntities2;

    @OneToMany(mappedBy = "thirdEntity1", fetch= FetchType.EAGER) // Noncompliant
    private Collection<SomeEntity> thirdEntities1;

    @ManyToMany(mappedBy = "thirdEntity1", fetch= FetchType.EAGER) // Noncompliant
    private Collection<SomeEntity> thirdEntities2;

    @OneToMany(fetch = FetchType.LAZY) // Compliant
    private Collection<SomeEntity> fourthEntities1;

    @ManyToMany(fetch = FetchType.LAZY) // Compliant
    private Collection<SomeEntity> fourthEntities2;

}

