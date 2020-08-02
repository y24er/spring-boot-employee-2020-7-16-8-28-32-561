package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.Mapper.RequestMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMapperTest {
    @Test
    void should_return_company_when_to_company_given_company_request_dto() {
        //given
        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO("oocl", Collections.EMPTY_LIST);
        RequestMapper requestMapper = new RequestMapper();
        //when
        Company company = requestMapper.toCompany(companyRequestDTO);
        //then
        assertEquals("oocl", company.getCompanyName());
    }
}
