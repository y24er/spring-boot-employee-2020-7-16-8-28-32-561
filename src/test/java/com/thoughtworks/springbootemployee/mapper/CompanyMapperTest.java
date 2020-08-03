package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyMapperTest {
    @Test
    void should_return_company_when_to_company_given_company_request_dto() {
        //given
        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO("oocl", Collections.EMPTY_LIST);
        CompanyMapper requestMapper = new CompanyMapper();
        //when
        Company company = requestMapper.toCompany(companyRequestDTO);
        //then
        assertEquals("oocl", company.getCompanyName());
    }
    @Test
    void should_return_company_response_dto_when_to_response_dto_given_company() {
        //given
        CompanyMapper companyMapper = new CompanyMapper();
        Company company = new Company(1, "oocl", asList(new Employee(1, "Jessie", 17, "female", 10000.0)));
        //when
        CompanyResponseDTO companyResponseDTO = companyMapper.toCompanyResponseDTO(company);
        //then
        assertEquals("oocl", companyResponseDTO.getCompanyName());
        assertEquals(1, companyResponseDTO.getEmployeesName().size());
        assertEquals("Jessie", companyResponseDTO.getEmployeesName().get(0));
    }
}
