package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
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

}
