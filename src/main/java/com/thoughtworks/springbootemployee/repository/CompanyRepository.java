package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    List<Company> companies = new ArrayList<>();
    public List<Company> getCompanies() {
        return null;
    }

    public Company getCompany(int ID) {
        return null;
    }

    public List<Employee> getEmployees(int ID) {
        return null;
    }

    public Company addCompany(Company company) {
        return company;
    }
}
