/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2023 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

    public void smellGetAllEmployeesByIdsForEach() {
        List<Employee> employees = new ArrayList<>();
        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.forEach(id -> {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        });
    }

    public void smellGetAllEmployeesByIdsForEachOrdered() {
        List<Employee> employees = new ArrayList<>();
        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.forEachOrdered(id -> {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        });
    }

    public void smellGetAllEmployeesByIdsMap() {
        List<Employee> employees = new ArrayList<>();
        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.map(id -> {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        });
    }

    public void smellGetAllEmployeesByIdsPeek() {
        List<Employee> employees = new ArrayList<>();
        Stream stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.peek(id -> {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        });
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        return ids
                .stream()
                .map(element -> {
                    return employeeRepository.findById(element).orElse(null);// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        Stream stream = ids.stream();
        return stream.map(element -> {
                    return employeeRepository.findById(element).orElse(null);// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        Stream stream = ids.stream();
        return stream.map(element -> {
                    return employeeRepository.findById(element);// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIdsWithoutStream(List<Integer> ids) {
        return employeeRepository.findAllById(ids); // Compliant
    }

    public List<Employee> smellDeleteEmployeeById(List<Integer> ids) {
        Stream stream = ids.stream();
        return stream.map(element -> {
                    return employeeRepository.deleteById(element);// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

    public List<Employee> smellGetAllEmployeesByIdsWithSeveralMethods(List<Integer> ids) {
        Stream stream = ids.stream();
        return stream.map(element -> {
                    return employeeRepository.findById(element).orElse(null).anotherMethod().anotherOne();// Noncompliant {{Avoid Spring repository call in loop or stream}}
                })
                .collect(Collectors.toList());
    }

    public class Employee {
    }

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    }
}