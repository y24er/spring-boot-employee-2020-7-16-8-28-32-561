package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public Company toCompany(CompanyRequestDTO companyRequestDTO) {
        Company company = new Company();
        company.setCompanyName(companyRequestDTO.getCompanyName());
        company.setEmployees(companyRequestDTO.getEmployees());
        return company;
    }
}
