import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
public interface UserRepository extends CrudRepository<User, Long> {


    User findById();

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    List<User> findWithoutNPlusOne();

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();
    @Query(value = "SELECT p FROM User p LEFT JOIN FETCH p.roles", nativeQuery = false)
    @Override
    List<User> findAllWithRoles();

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    @Override
    List<User> findAllWithAdminRoles();

}

public class User {

}
