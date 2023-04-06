import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
public interface UserRepository extends CrudRepository<User, Long> {


    User shloudSucceedBecauseDoesNotReturnAnIterable();

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    List<User> shouldSucceedBecauseQueryAnnotationContainsJointFetch();

    @EntityGraph(attributePaths = {"roles"})
    List<User> shouldSucceedBecauseIsAnnotatedWithEntityGraph();

    @Query(value = "SELECT p FROM User p LEFT JOIN FETCH p.roles", nativeQuery = false)
    @Override
    List<User> shouldSucceedBecauseIsAnnotatedSeveralTimesIncludingOnceQueryAnnotationContainsJointFetch();

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    @Override
    List<User> shouldSucceedBecauseIsAnnotatedSeveralTimesIncludingOnceQueryAnnotationContainsJointFetch();

}

public class User {

}
