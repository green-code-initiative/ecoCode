import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface UserRepository extends User {

    User findById();

    List<User> findWithoutNPlusOne();

    List<User> findAll();
    @Override
    List<User> findAllWithRoles();

}

public class User {

}
