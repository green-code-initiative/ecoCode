import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll(); // Noncompliant {{Avoid N+1 Query problem}}

    @Override // Noncompliant {{Avoid N+1 Query problem}}
    List<User> findByIds();

    @Query(value = "SELECT p FROM User p LEFT JOIN p.roles", nativeQuery = false) // Noncompliant {{Avoid N+1 Query problem}}
    List<User> findAllWithRoles();
}

public class User {

}
