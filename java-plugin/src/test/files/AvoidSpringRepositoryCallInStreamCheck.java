package fr.greencodeinitiative.java.checks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AvoidSpringRepositoryCallInStreamCheck {

    @Autowired
    private EmployeeRepository employeeRepository;

//    public void smellGetAllEmployeesByIdsForEach() {
//        List<Employee> employees = new ArrayList<>();
//        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        stream.forEach(id -> {
//            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
//            if (employee.isPresent()) {
//                employees.add(employee.get());
//            }
//        });
//    }
//
//    public void smellGetAllEmployeesByIdsForEachOrdered() {
//        List<Employee> employees = new ArrayList<>();
//        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        stream.forEachOrdered(id -> {
//            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
//            if (employee.isPresent()) {
//                employees.add(employee.get());
//            }
//        });
//    }
//
//    public void smellGetAllEmployeesByIdsMap() {
//        List<Employee> employees = new ArrayList<>();
//        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        stream.map(id -> {
//            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
//            if (employee.isPresent()) {
//                employees.add(employee.get());
//            }
//        });
//    }
//
//    public void smellGetAllEmployeesByIdsPeek() {
//        List<Employee> employees = new ArrayList<>();
//        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        stream.peek(id -> {
//            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
//            if (employee.isPresent()) {
//                employees.add(employee.get());
//            }
//        });
//    }
//
//    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
//        return ids
//                .stream()
//                .map(element -> {
//                    return employeeRepository.findById(element).orElse(null);// Noncompliant {{Avoid Spring repository call in loop or stream}}
//                })
//                .collect(Collectors.toList());
//    }
//
    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        Stream stream = ids.stream();
        return stream.map(element -> {
                    return employeeRepository.findById(element).orElse(null);// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

//    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
//        Stream stream = ids.stream();
//        return stream.map(element -> {
//                    return employeeRepository.findById(element);// Noncompliant {{Avoid Spring repository call in loop or stream}}
//                })
//                .collect(Collectors.toList());
//    }

//    public List<Employee> smellGetAllEmployeesByIdsWithoutStream(List<Integer> ids) {
//        return   employeeRepository.findAllById(ids); // Compliant
//    }

    public class Employee {
    }

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    }
}