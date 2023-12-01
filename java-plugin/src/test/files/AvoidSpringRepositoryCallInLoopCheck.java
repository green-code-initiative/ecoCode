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

public class AvoidSpringRepositoryCallInLoopCheck {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> smellGetAllEmployeesByIds(List<Integer> ids) {
        List<Employee> employees = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Employee> employee = employeeRepository.findById(id); // Noncompliant {{Avoid Spring repository call in loop or stream}}
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        }
        return employees;
    }

    public class Employee {
        private Integer id;
        private String name;

        public Employee(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() { return id; }
        public String getName() { return name; }
    }

    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    }

}