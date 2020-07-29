package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public static List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getEmployee(Integer page, Integer pageSize, String gender) {
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        if (page != null && pageSize != null) {
            return employees.subList(--page * pageSize, ++page * pageSize);
        } else if (gender != null) {
            return employees.stream().filter(employee -> Objects.equals(employee.getGender(), gender)).collect(Collectors.toList());
        }
        return employees;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employees.stream().filter(employee -> employee.getId() == 1).findAny().orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee2) {
        Employee employee = employees.stream().filter(employee1 -> employee1.getId() == id).findAny().orElse(null);
        employees.remove(employee);
        employees.add(employee2);
        return employees.stream().filter(employee1 -> employee1.getId() == id).findAny().orElse(null);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteEmployee(@PathVariable int id) {
        Employee employee = employees.stream().filter(employee1 -> employee1.getId() == id).findAny().orElse(null);
        return employees.remove(employee);
    }

}
