package fr.greencodeinitiative.java.checks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AvoidSpringRepositoryCallInStreamCheck {
    private EmployeeRepository employeeRepository;

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
      return   ids.stream().map(element ->
      {
          return employeeRepository.findById(element).orElse(null);// Noncompliant
          }
        ).collect(Collectors.toList());
    }




    public List<Employee> smellGetAllEmployeesByIdsWithoutStream(List<Integer> ids) {
        return   employeeRepository.findAllById(ids);// Compliant
    }


    public class Employee {}

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}

}