package fr.greencodeinitiative.java.checks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AvoidSpringRepositoryCallInStreamCheck {
    private EmployeeRepository employeeRepository;

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
      return   ids.stream().map(element ->
                     employeeRepository.findById(element).orElse(null)// Noncompliant
        ).collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        return   ids.stream().filter(element -> element == 1).map(element ->
                employeeRepository.findById(element).orElse(null)// Noncompliant
        ).collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        return   ids.stream().filter(element ->
                element == 1// Compliant
        ).collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        return   employeeRepository.findAllById(ids);// Compliant
    }


    public class Employee {}

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}

}