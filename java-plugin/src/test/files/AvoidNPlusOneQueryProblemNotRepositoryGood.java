package fr.greencodeinitiative.java.checks;

import java.util.List;
public interface AvoidNPlusOneQueryProblemNotRepositoryGood extends CustomerRepository {

    Customer findById();

    List<Customer> findWithoutNPlusOne();

    List<Customer> findAll();
    @Deprecated
    List<Customer> findAllWithRoles();

}

interface CustomerRepository {

}

class Customer {

}
