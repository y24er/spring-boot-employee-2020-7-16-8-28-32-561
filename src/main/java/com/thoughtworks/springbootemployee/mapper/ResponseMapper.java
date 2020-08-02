package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    public CompanyResponseDTO toCompanyResponseDTO(Company company) {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        companyResponseDTO.setId(company.getId());
        companyResponseDTO.setCompanyName(company.getCompanyName());
        List<String> nameList = company.getEmployees().stream().map(Employee::getName).collect(Collectors.toList());
        companyResponseDTO.setEmployeesName(nameList);
        return companyResponseDTO;
    }
}
