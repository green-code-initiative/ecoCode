package fr.greencodeinitiative.java.checks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AvoidRepositoryCallInLoopCheck {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        List<Employee> employees = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        }
        return employees;
    }

    public class Employee {
    }

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    }
    
}