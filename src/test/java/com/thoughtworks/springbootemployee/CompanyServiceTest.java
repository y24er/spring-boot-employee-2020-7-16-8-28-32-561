package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies() {
        //given
        when(companyRepository.findAll()).thenReturn(asList(new Company(1, 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)), "oocl")));
        //when
        List<Company> companies = companyService.getCompanies();
        //then
        assertEquals(1, companies.size());
    }

    @Test
    void should_return_right_company_when_get_company_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)), "oocl")));
        //when
        Company company = companyService.getCompany(1);
        //then
        assertEquals(1, company.getId());
    }

    @Test
    void should_return_employees_when_get_employees_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)), "oocl")));
        //when
        List<Employee> employees = companyService.getEmployees(1);
        //then
        assertEquals(2, employees.size());
    }

//    @Test
//    void should_return_company_when_add_company_given_company() {
//        //given
//        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)), "oocl")));
//        //when
//        Company company = companyService.addCompany(new Company(1, 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)), "oocl"));
//        //then
//        assertEquals(1, company.getId());
//    }

//    @Test
//    void should_return_updated_company_when_update_company_given_company() {
//        //given
//        given(mockedCompanyRepository.getCompany(any())).willReturn(mockedCompanies.get(0));
//
//        given(mockedCompanyRepository.update(any())).willReturn(mockedCompanies.get(0));
//        //when
//        Company company = companyService.updateCompany(mockedCompanies.get(0));
//        //then
//        assertEquals(mockedCompanies.get(0), company);
//    }

//    @Test
//    void should_return_null_update_company_given_company_not() {
//        //given
//        System.out.println(mockedCompanies.get(0));
//        companyService.addCompany(mockedCompanies.get(0));
//        System.out.println(companyService.getCompanies());
//        //when
//
//        //then
//    }
}
