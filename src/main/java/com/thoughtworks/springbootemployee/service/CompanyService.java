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
        return companyRepository.findAll();
    }

    public Company getCompany(int ID) {
        return companyRepository.findById(ID).get();
    }

    public List<Employee> getEmployees(int ID) {
        return companyRepository.findById(ID).get().getEmployees();
    }

    public Company addCompany(Company company) {
        companyRepository.save(company);
        return company;
    }

    public Company updateCompany(Company company) {
        Company company1 = companyRepository.findById(company.getId()).get();
        if (company.getCompanyName() != null) {
            company1.setCompanyName(company.getCompanyName());
        }
        if (company.getEmployees() != null) {
            company1.setEmployees(company.getEmployees());
        }
        if (company.getEmployeesNums() != 0) {
            company1.setEmployeesNums(company.getEmployeesNums());
        }
        return companyRepository.save(company);
    }
}
