package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    public Company getCompany(int ID) {
        return companyRepository.getCompany(ID);
    }

    public List<Employee> getEmployees(int ID) {
        return companyRepository.getEmployees(ID);
    }

    public Company addCompany(Company company) {
        return companyRepository.addCompany(company);
    }
}
