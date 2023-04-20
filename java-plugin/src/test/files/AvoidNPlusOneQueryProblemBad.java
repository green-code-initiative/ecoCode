package fr.greencodeinitiative.java.checks;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

public interface AvoidNPlusOneQueryProblemBad extends CrudRepository<User, Long> {

    List<User> shouldFailBecauseIsNotAnnotated(); // Noncompliant {{Avoid N+1 Query problem}}

    @Deprecated // Noncompliant {{Avoid N+1 Query problem}}
    List<User> shouldFailBecauseIsNotAnnotatedWithARightAnnotation();

    @Query(value = "SELECT p FROM User p LEFT JOIN p.roles", nativeQuery = false) // Noncompliant {{Avoid N+1 Query problem}}
    List<User> shouldFailBecauseQueryAnnotationDoesNotContainsJointFetch();

    @Query("SELECT p FROM User p LEFT JOIN p.roles") // Noncompliant {{Avoid N+1 Query problem}}
    List<User> shouldFailBecauseQueryAnnotationDoesNotContainsJointFetchValue();
}

@Entity
class User {

    @OneToMany
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

@Entity
class Role {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
