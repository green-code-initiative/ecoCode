import org.springframework.data.repository.CrudRepository;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll(); // Noncompliant {{Avoid N+1 Query problem}}

    @Override // Noncompliant {{Avoid N+1 Query problem}}
    List<User> findByIds();

    @Query(value = "SELECT p FROM User p LEFT JOIN p.roles", nativeQuery = false) // Noncompliant {{Avoid N+1 Query problem}}
    List<User> findAllWithRoles();
}

@Entity
public class User {

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
public class Role {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
