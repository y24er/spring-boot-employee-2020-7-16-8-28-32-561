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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies() {
        //given
        when(companyRepository.findAll()).thenReturn(asList(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)))));
        //when
        List<Company> companies = companyService.getCompanies();
        //then
        assertEquals(1, companies.size());
    }

    @Test
    void should_return_right_company_when_get_company_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)))));
        //when
        Company company = companyService.getCompany(1);
        //then
        assertEquals(1, company.getId());
    }

    @Test
    void should_return_null_company_when_get_company_given_not_exist_company_id() {
        //given
        when(companyRepository.findById(7)).thenReturn(null);
        //when
        Company company = companyService.getCompany(7);
        //then
        assertNull(company);
    }

    @Test
    void should_return_employees_when_get_employees_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)))));
        //when
        List<Employee> employees = companyService.getEmployees(1);
        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_company_when_add_company_given_company() {
        //given
        Company company = new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))));
        //when
        Company savedCompany = companyService.addCompany(company);
        //then
        assertEquals(1, savedCompany.getId());
    }

    @Test
    void should_return_updated_company_when_update_company_given_id_and_updated_company_info() {
        //given
        Company company = new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        companyRepository.save(company);

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        //when
        Company updatedCompany = companyService.updateCompany(1, new Company(1, null, 100, null));
        //then
        assertEquals(1, updatedCompany.getId());
        assertEquals("oocl", updatedCompany.getCompanyName());
        assertEquals(100, updatedCompany.getEmployeesNumber());
    }

    @Test
    void should_return_updated_company_when_update_company_given_id_and_no_updated_company_info() {
        //given
        Company company = new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        companyRepository.save(company);

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        //when
        Company updatedCompany = companyService.updateCompany(1, new Company(1, null, 0, null));
        //then
        assertEquals(1, updatedCompany.getId());
        assertEquals("oocl", updatedCompany.getCompanyName());
        assertEquals(2, updatedCompany.getEmployeesNumber());
    }

    @Test
    void should_return_null_update_company_given_not_exist_company_id() {
        //given
        Company company = new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        companyRepository.save(company);
        //when
        when(companyRepository.findById(2)).thenReturn(null);
        Company updatedCompany = companyService.updateCompany(2, new Company(2, null, 100, null));
        //then
        assertNull(updatedCompany);
    }

    @Test
    void should_return_void_when_delete_company_given_company_id() {
        //given
        int companyId = 1;
        //when
        companyService.deleteCompanyById(companyId);
        //then
        verify(companyRepository, times(1)).deleteById(1);
    }

    @Test
    void should_return_companies_when_get_companies_by_page_given_pageNum_and_pageSize() {
        //given
        List<Company> companies = asList(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))), new Company(2, "oocl", 2, asList(new Employee(3, "alibaba1", 20, "male", 6000.0), new Employee(4, "alibaba2", 19, "male", 8000.0))));
        when(companyRepository.saveAll(companies)).thenReturn(companies);
        companyRepository.saveAll(companies);
        when(companyRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(asList(new Company(1, "oocl", 2, asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))))));
        int pageNum = 0;
        int pageSize = 1;
        //when
        Page companyPage = companyService.getCompaniesByPage(pageNum, pageSize);
        //then
        assertEquals(1, companyPage.getContent().size());
        assertEquals(1, ((Company) companyPage.getContent().get(0)).getId());
    }
}
