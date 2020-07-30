package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(int employeeID) {
        Optional<Employee> optional = employeeRepository.findById(employeeID);
        return optional.isPresent() ? optional.get() : null;
    }

    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee updateEmployee(int employeeID, Employee employee) {
        Optional result = employeeRepository.findById(employeeID);
        if (result != null) {
            Employee oldEmployee = (Employee) result.get();
            System.out.println(oldEmployee.getId());
            if (employee.getName() != null) {
                oldEmployee.setName(employee.getName());
            }
            if (employee.getAge() != 0) {
                oldEmployee.setAge(employee.getAge());
            }
            if (employee.getGender() != null) {
                oldEmployee.setGender(employee.getGender());
            }
            if (employee.getSalary() != null) {
                oldEmployee.setSalary(employee.getSalary());
            }
            employeeRepository.save(oldEmployee);
            return employee;
        }
        return null;
    }

    public String deleteEmployee(int employeeID) {
        employeeRepository.deleteById(employeeID);
        return "delete success";
    }

    public List<Employee> getEmployees(Integer pageNum, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNum - 1, pageSize)).toList();
    }
}
