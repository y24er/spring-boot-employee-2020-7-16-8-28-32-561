package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Exception.NotFoundCompanyException;
import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.RequestMapper;
import com.thoughtworks.springbootemployee.mapper.ResponseMapper;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private RequestMapper requestMapper;
    @Mock
    private ResponseMapper responseMapper;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies() {
        //given
        List<Company> companies = asList(new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))));
        when(companyRepository.findAll()).thenReturn(companies);

        when(responseMapper.toCompanyResponseDTO(companies.get(0))).thenReturn(new CompanyResponseDTO(1, "oocl", asList("alibaba1", "alibaba2")));
        //when
        List<CompanyResponseDTO> companyResponseDTOs = companyService.getCompanies();
        //then
        assertEquals(1, companyResponseDTOs.size());
    }

    @Test
    void should_return_right_company_when_get_company_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)))));
        //when
        Company company = companyService.getCompany(1);
        //then
        assertEquals(1, company.getId());
    }

    @Test
    void should_return_null_company_when_get_company_given_not_exist_company_id() {
        //given
        when(companyRepository.findById(7)).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(NotFoundCompanyException.class, () -> companyService.getEmployees(7));
        //then
        assertEquals("Not exist this company!", exception.getMessage());
    }

    @Test
    void should_return_employees_when_get_employees_given_company_id() {
        //given
        when(companyRepository.findById(1)).thenReturn(Optional.of(new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)))));
        //when
        List<Employee> employees = companyService.getEmployees(1);
        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_company_when_add_company_given_company() {
        //given
        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO("oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        Company company = new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(requestMapper.toCompany(companyRequestDTO)).thenReturn(company);

        when(companyRepository.save(company)).thenReturn(company);
        //when
        Company savedCompany = companyService.addCompany(companyRequestDTO);
        //then
        assertEquals(1, savedCompany.getId());
    }

    @Test
    void should_return_updated_company_when_update_company_given_id_and_updated_company_info() {
        //given
        Company company = new Company(1, "alibaba", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        companyRepository.save(company);

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO("oocl", null);
        when(requestMapper.toCompany(companyRequestDTO)).thenReturn(new Company("oocl", null));

        //when
        Company updatedCompany = companyService.updateCompany(1, companyRequestDTO);
        //then
        assertEquals(1, updatedCompany.getId());
        assertEquals("oocl", updatedCompany.getCompanyName());
    }

    @Test
    void should_return_null_update_company_given_not_exist_company_id() {
        //given
        Company company = new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        companyRepository.save(company);

        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO(null, null);
        when(requestMapper.toCompany(companyRequestDTO)).thenReturn(new Company(null, null));

        //when
        when(companyRepository.findById(2)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(NotFoundCompanyException.class, () -> companyService.updateCompany(2, companyRequestDTO));
        //then
        assertEquals("Not exist this company!", exception.getMessage());
    }

    @Test
    void should_return_void_when_delete_company_given_company_id() {
        //given
        Company company = new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0)));
        when(companyRepository.save(company)).thenReturn(company);
        Company savedCompany = companyRepository.save(company);

        when(companyRepository.existsById(1)).thenReturn(true);
        int companyId = savedCompany.getId();
        //when
        companyService.deleteCompanyById(companyId);
        //then
        verify(companyRepository, times(1)).deleteById(1);
    }

    @Test
    void should_throw_not_found_company_exception_when_delete_company_given_not_exist_company_id() {
        //given
        when(companyRepository.existsById(7)).thenReturn(false);
        //when
        Throwable exception = assertThrows(NotFoundCompanyException.class, () -> companyService.deleteCompanyById(7));
        //then
        assertEquals("Not exist this company!", exception.getMessage());
    }

    @Test
    void should_return_companies_when_get_companies_by_page_given_pageNum_and_pageSize() {
        //given
        List<Company> companies = asList(new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))), new Company(2, "oocl", asList(new Employee(3, "alibaba1", 20, "male", 6000.0), new Employee(4, "alibaba2", 19, "male", 8000.0))));
        when(companyRepository.saveAll(companies)).thenReturn(companies);
        companyRepository.saveAll(companies);
        when(companyRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(asList(new Company(1, "oocl", asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0))))));
        int pageNum = 0;
        int pageSize = 1;
        //when
        Page companyPage = companyService.getCompaniesByPage(pageNum, pageSize);
        //then
        assertEquals(1, companyPage.getContent().size());
        assertEquals(1, ((Company) companyPage.getContent().get(0)).getId());
    }
}
