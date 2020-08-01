package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployee(String gender) {
        if (gender != null) {
            return employeeService.getEmployeesByGender(gender);
        }
        return employeeService.getAllEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        if (page != 0 && pageSize != 0) {
            return employeeService.getEmployeeByPage(page, pageSize);
        } else {
            return new PageImpl<>(employeeService.getAllEmployees());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployeeByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee2) {
        return employeeService.updateEmployee(id, employee2);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

}
