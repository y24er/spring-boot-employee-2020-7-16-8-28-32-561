package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRequestDTO companyRequestDTO) {
        Company company = new Company();
        company.setCompanyName(companyRequestDTO.getCompanyName());
        company.setEmployees(companyRequestDTO.getEmployees());
        return company;
    }
    public CompanyResponseDTO toCompanyResponseDTO(Company company) {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        companyResponseDTO.setId(company.getId());
        companyResponseDTO.setCompanyName(company.getCompanyName());
        List<String> nameList = company.getEmployees().stream().map(Employee::getName).collect(Collectors.toList());
        companyResponseDTO.setEmployeesName(nameList);
        return companyResponseDTO;
    }
}
