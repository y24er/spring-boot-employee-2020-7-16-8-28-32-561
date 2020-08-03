package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundCompanyException;
import com.thoughtworks.springbootemployee.exception.NotFoundEmployeeException;
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
        Optional<Employee> result = employeeRepository.findById(employeeID);
        if (!result.isPresent()) {
            throw new NotFoundEmployeeException();
        }
        return result.get();
    }

    public Employee addEmployee(Employee employee) {
        int companyId = employee.getCompanyId();
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundCompanyException();
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee updateEmployee(int employeeID, Employee employee) {
        Employee oldEmployee = employeeRepository.findById(employeeID).orElseThrow(()->{throw new NotFoundEmployeeException();});
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
        Employee updatedEmployee = employeeRepository.save(oldEmployee);
        return updatedEmployee;
    }

    public void deleteEmployee(int employeeID) {
        if (!employeeRepository.existsById(employeeID)) {
            throw new NotFoundEmployeeException();
        }
        employeeRepository.deleteById(employeeID);
    }

    public Page<Employee> getEmployeeByPage(Integer pageNum, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
