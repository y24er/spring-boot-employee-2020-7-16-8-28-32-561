package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    @Test
    void should_return_companies_when_get_companies() {
        //given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        List<Company> mockedCompanies = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        mockedCompanies.add(new Company(1, 2, employees, "alibaba"));
        given(mockedCompanyRepository.getCompanies()).willReturn(mockedCompanies);

        //when
        List<Company> companies = companyService.getCompanies();

        //then
        assertEquals(mockedCompanies.size(), companies.size());

    }
}
