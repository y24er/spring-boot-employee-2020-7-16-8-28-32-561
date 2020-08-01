package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(int employeeID) {
        Optional<Employee> optional = employeeRepository.findById(employeeID);
        return optional.isPresent() ? optional.get() : null;
    }

    public Employee addEmployee(Employee employee) {
        int companyId = employee.getCompanyId();
        if (companyRepository.existsById(companyId)) {
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee updateEmployee(int employeeID, Employee employee) {
        Optional result = employeeRepository.findById(employeeID);
        if (result != null) {
            Employee oldEmployee = (Employee) result.get();
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
            System.out.println(oldEmployee);
            Employee updatedEmployee = employeeRepository.save(oldEmployee);
            return updatedEmployee;
        }
        return null;
    }

    public void deleteEmployee(int employeeID) {
        employeeRepository.deleteById(employeeID);
    }

    public Page<Employee> getEmployeeByPage(Integer pageNum, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
