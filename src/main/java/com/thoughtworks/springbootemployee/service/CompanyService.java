package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundCompanyException;
import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;
    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public List<CompanyResponseDTO> getCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponseDTO> companyResponseDTOS = new ArrayList<>();
        for (Company company : companies) {
            companyResponseDTOS.add(companyMapper.toCompanyResponseDTO(company));
        }
        return companyResponseDTOS;
    }

    public Company getCompany(int ID) {
        Optional<Company> result = companyRepository.findById(ID);
        if (!result.isPresent()) {
            throw new NotFoundCompanyException();
        }
        return result.get();

    }

    public List<Employee> getEmployees(int ID) {
        Company company = companyRepository.findById(ID).orElseThrow(() -> {
            throw new NotFoundCompanyException();
        });
        return company.getEmployees();
    }

    public CompanyResponseDTO addCompany(CompanyRequestDTO companyRequestDTO) {
        Company company = companyMapper.toCompany(companyRequestDTO);
        List<Employee> employees = company.getEmployees();
        company.setEmployees(Collections.emptyList());
        Company savedCompany = companyRepository.save(company);
        if (employees != null) {
            employees.forEach(employee -> employee.setCompanyId(savedCompany.getId()));
        }
        savedCompany.setEmployees(employees);
        Company company1 = companyRepository.save(savedCompany);
        return companyMapper.toCompanyResponseDTO(company1);

    }

    public Company updateCompany(Integer companyId, CompanyRequestDTO companyRequestDTO) {
        Company company = companyMapper.toCompany(companyRequestDTO);
        Company oldCompany = companyRepository.findById(companyId).orElseThrow(() -> {
            throw new NotFoundCompanyException();
        });
        if (company.getCompanyName() != null) {
            oldCompany.setCompanyName(company.getCompanyName());
        }
        if (company.getEmployees() != null) {
            oldCompany.setEmployees(company.getEmployees());
        }
        return companyRepository.save(oldCompany);
    }

    public void deleteCompanyById(int companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundCompanyException();
        }
        companyRepository.deleteById(companyId);
    }

    public Page getCompaniesByPage(int pageNum, int pageSize) {
        return companyRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
