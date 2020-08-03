package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.ResponseMapper;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseMapperTest {
    @Test
    void should_return_company_response_dto_when_to_response_dto_given_company() {
        //given
        ResponseMapper ResponseMapper = new ResponseMapper();
        Company company = new Company(1, "oocl", asList(new Employee(1, "Jessie", 17, "female", 10000.0)));
        //when
        CompanyResponseDTO companyResponseDTO = ResponseMapper.toCompanyResponseDTO(company);
        //then
        assertEquals("oocl", companyResponseDTO.getCompanyName());
        assertEquals(1, companyResponseDTO.getEmployeesName().size());
        assertEquals("Jessie", companyResponseDTO.getEmployeesName().get(0));
    }
}
