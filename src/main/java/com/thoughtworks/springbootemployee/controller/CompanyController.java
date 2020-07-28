package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @GetMapping
    public List<Company> getCompanies() {
        List<Company> companies=new ArrayList<>();
        companies.add(new Company(1));
        companies.add(new Company(2));
        return companies;
    }
    @GetMapping("{id}")
    public Company getCompany(@PathVariable int id) {
        Company company=new Company(id);
        return company;
    }
    @GetMapping("{id}/employees")
    public List<Employee> getEmployeesOfCompany(@PathVariable int id) {
        List<Employee> employees=new ArrayList<>();
        employees.add(new Employee(1));
        employees.add(new Employee(2));
        employees.add(new Employee(3));
        Company company=new Company(id);
        company.setEmployees(employees);
        List<Employee> companyEmployees = company.getEmployees();
        return companyEmployees;
    }

}
