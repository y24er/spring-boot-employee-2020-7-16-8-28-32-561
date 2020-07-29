package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    List<Company> mockedCompanies = new ArrayList<>();
    List<Employee> employees = new ArrayList<>();
    CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
    CompanyService companyService = new CompanyService(mockedCompanyRepository);

    @BeforeEach
    void setUp() {
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
        employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
        mockedCompanies.add(new Company(1, 2, employees, "alibaba"));
    }

    @Test
    void should_return_companies_when_get_companies() {
        //given
        given(mockedCompanyRepository.findAll()).willReturn(mockedCompanies);
        //when
        List<Company> companies = companyService.getCompanies();
        //then
        assertEquals(mockedCompanies.size(), companies.size());
    }

    @Test
    void should_return_right_company_when_get_company_given_company_id() {
        //given
        given(mockedCompanyRepository.findById(1).get()).willReturn(mockedCompanies.get(0));
        //when
        Company company = companyService.getCompany(1);
        //then
        assertEquals(mockedCompanies.get(0), company);
    }

    @Test
    void should_return_employees_when_get_employees_given_company_id() {
        //given
        given(mockedCompanyRepository.findById(any()).get().getEmployees()).willReturn(mockedCompanies.get(0).getEmployees());
        //when
        List<Employee> employees = companyService.getEmployees(1);
        //then
        assertEquals(mockedCompanies.get(0).getEmployees(), employees);
    }

    @Test
    void should_return_company_when_add_company_given_company() {
        //given
        given(mockedCompanyRepository.save(mockedCompanies.get(0))).willReturn(mockedCompanies.get(0));
        //when
        Company company = companyService.addCompany(mockedCompanies.get(0));
        //then
        assertEquals(mockedCompanies.get(0), company);
    }
//
//    @Test
////    void should_return_updated_company_when_update_company_given_company() {
////        //given
////        given(mockedCompanyRepository.getCompany(any())).willReturn(mockedCompanies.get(0));
////
////        given(mockedCompanyRepository.update(any())).willReturn(mockedCompanies.get(0));
////        //when
////        Company company = companyService.updateCompany(mockedCompanies.get(0));
////        //then
////        assertEquals(mockedCompanies.get(0), company);
////    }

    @Test
    void should_return_null_update_company_given_company_not() {
        //given
        System.out.println(mockedCompanies.get(0));
        companyService.addCompany(mockedCompanies.get(0));
        System.out.println(companyService.getCompanies());
        //when

        //then
    }
}
