import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Should succeed because is not a repository
 */
public interface UserRepository extends User {

    User findById();

    List<User> findWithoutNPlusOne();

    List<User> findAll();
    @Override
    List<User> findAllWithRoles();

}

public class User {

}
