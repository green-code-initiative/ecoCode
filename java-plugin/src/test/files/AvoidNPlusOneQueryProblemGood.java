package fr.greencodeinitiative.java.checks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
public interface AvoidNPlusOneQueryProblemGood extends CrudRepository<Client, Long> {


    Client shouldSucceedBecauseDoesNotReturnAnIterable();

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    List<Client> shouldSucceedBecauseQueryAnnotationContainsJointFetch();

    @EntityGraph(attributePaths = {"roles"})
    List<Client> shouldSucceedBecauseIsAnnotatedWithEntityGraph();
    @Query(value = "SELECT p FROM Client p LEFT JOIN FETCH p.roles", nativeQuery = false)
    @Deprecated
    List<Client> shouldSucceedBecauseIsAnnotatedSeveralTimesIncludingOnceQueryAnnotationContainsJointFetch();

    @Query("SELECT p FROM Client p LEFT JOIN FETCH p.roles")
    @Deprecated
    List<Client> shouldSucceedBecauseIsAnnotatedSeveralTimesIncludingOnceQueryAnnotationContainsJointFetchValue();

}

class Client {

}
