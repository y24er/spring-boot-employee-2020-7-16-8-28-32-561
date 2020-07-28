package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    static List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getCompanies(Integer page, Integer pageSize) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        companies.add(new Company(1, 2, employees, "alibaba"));
        companies.add(new Company(2, 2, employees, "alibaba2"));
        if (page != null && pageSize != null) {
            return companies.subList(page * --pageSize, page * pageSize);
        }
        return companies;
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
    public Map<Object, Object> addCompany(Company company) {
        HashMap<Object, Object> resultMap = new HashMap<>(2);
        companies.add(company);
        resultMap.put("status", 200);
        resultMap.put("message", "添加成功");
        return resultMap;
    }

    @PutMapping("companies/{id}")
    public Company updateCompany(@PathVariable int id) {

        return companies.stream().filter(company -> company.getId() == id).findAny().orElse(null);
    }

    @DeleteMapping("companies/{id}")
    public boolean deleteCompany(@PathVariable int id) {
        Company company = companies.stream().filter(company1 -> company1.getId() == id).findAny().orElse(null);
        return companies.remove(company);
    }

}
