package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    static List<Company> companies = new ArrayList<>();
    @Autowired
    CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies(Integer page, Integer pageSize) {
//        List<Employee> employees = new ArrayList<>();
//        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
//        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
//        companies.add(new Company(1, 2, employees, "alibaba"));
//        companies.add(new Company(2, 2, employees, "alibaba2"));
//        if (page != null && pageSize != null) {
//            return companies.subList(--page * pageSize, ++page * pageSize);
//        }
        return companyService.getCompanies();
    }

    @GetMapping("{id}")
    public Company getCompany(@PathVariable int id) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        return new Company(1, 2, employees, "alibaba");
    }

    @GetMapping("{id}/employees")
    public List<Employee> getEmployeesOfCompany(@PathVariable int id) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        Company company = new Company(1, 2, employees, "alibaba");
        return company.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("companies/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Company updateCompany(@PathVariable int id, @RequestBody Company company2) {
        Company company = companies.stream().filter(company1 -> company1.getId() == id).findAny().orElse(null);
        companies.remove(company);
        companies.add(company2);
        return companies.stream().filter(company1 -> company1.getId() == id).findAny().orElse(null);
    }

    @DeleteMapping("companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteCompany(@PathVariable int id) {
        Company company = companies.stream().filter(company1 -> company1.getId() == id).findAny().orElse(null);
        return companies.remove(company);
    }


}
