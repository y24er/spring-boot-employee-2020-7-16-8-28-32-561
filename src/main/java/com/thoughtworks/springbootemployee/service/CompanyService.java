package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional result = companyRepository.findById(ID);
        if (result != null) {
            return (Company) result.get();
        } else {
            return null;
        }
    }

    public List<Employee> getEmployees(int ID) {
        return companyRepository.findById(ID).get().getEmployees();
    }

    public Company addCompany(Company company) {
        List<Employee> employees = company.getEmployees();
        company.setEmployees(Collections.emptyList());
        Company savedCompany = companyRepository.save(company);
        if (employees != null) {
            employees.forEach(employee -> employee.setCompanyId(savedCompany.getId()));
        }
        savedCompany.setEmployees(employees);
        return companyRepository.save(savedCompany);
    }

    public Company updateCompany(Integer companyId, Company company) {
        Optional result = companyRepository.findById(companyId);
        if (result != null) {
            Company oldCompany = (Company) result.get();
            if (company.getCompanyName() != null) {
                oldCompany.setCompanyName(company.getCompanyName());
            }
            if (company.getEmployees() != null) {
                oldCompany.setEmployees(company.getEmployees());
            }
            if (company.getEmployeesNumber() != 0) {
                oldCompany.setEmployeesNumber(company.getEmployeesNumber());
            }
            return companyRepository.save(oldCompany);
        }
        return null;

    }

    public void deleteCompanyById(int companyId) {
        companyRepository.deleteById(companyId);
    }

    public Page getCompaniesByPage(int pageNum, int pageSize) {
        return companyRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
