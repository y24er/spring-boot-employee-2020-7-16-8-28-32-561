package com.thoughtworks.springbootemployee.integration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegration {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void deleteData() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_find_all_companies_given_nothing() throws Exception {
        //given
        Company company = new Company("oocl", Collections.emptyList());
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "Hans", 24, "male", 5000.0);
        employee.setCompanyId(savedCompany.getId());
        savedCompany.setEmployees(asList(employee));
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].companyName").value("oocl"));
    }

    @Test
    void should_return_company_when_find_by_id_given_company_id() throws Exception {
        //given
        Company company = new Company("oocl", Collections.emptyList());
        Company savedCompany = companyRepository.save(company);

        int companyId = savedCompany.getId();
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(companyId));
    }

    @Test
    void should_return_employees_when_find_employees_by_company_id_given_company_id() throws Exception {
        //given
        Company company = new Company("oocl", Collections.emptyList());
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "Hans", 24, "male", 5000.0);
        employee.setCompanyId(savedCompany.getId());
        savedCompany.setEmployees(asList(employee));
        companyRepository.save(savedCompany);
        int companyId = savedCompany.getId();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", companyId))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1)).andExpect(jsonPath("$.[0].name").value("Hans"));
    }

    @Test
    void should_return_employees_when_find_employees_by_page_given_pageNun_and_pageSize() throws Exception {
        //given
        Company oneCompany = new Company("oocl", Collections.emptyList());
        Company OneSavedCompany = companyRepository.save(oneCompany);
        int oneCompanyId = OneSavedCompany.getId();
        Employee oneEmployee = new Employee("Jessie", 24, "male", 5000.0);
        oneEmployee.setCompanyId(oneCompanyId);
        OneSavedCompany.setEmployees(asList(oneEmployee));
        companyRepository.save(OneSavedCompany);
        int pageNum = 0;
        int pageSize = 1;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies").param("page", String.valueOf(pageNum)).param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1)).andExpect(jsonPath("$.[0].companyName").value("oocl"));
    }


    @Test
    void should_return_company_when_save_company_given_company() throws Exception {
        //given
        Company company = new Company("oocl", asList(new Employee("Jessie", 24, "male", 5000.0)));
        String requestJson = JSONObject.toJSONString(company);
        //when
        String responseContentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        //then
        assertTrue(responseContentAsString.contains("oocl"));
//        System.out.println(responseContentAsString);
        JSONObject jsonObject = JSONArray.parseObject(responseContentAsString);
        List<Employee> employees = JSONArray.parseArray(jsonObject.get("employees").toString(), Employee.class);
        assertEquals(1, employees.size());
    }

    @Test
    void should_return_company_when_update_company_given_company_id_and_updated_info() throws Exception {
        //given
        Company company = new Company("oocl", null);
        Company savedCompany = companyRepository.save(company);
        int companyId = savedCompany.getId();
        Employee employee = new Employee(1, "Hans", 24, "male", 5000.0);
        employee.setCompanyId(savedCompany.getId());
        savedCompany.setEmployees(asList(employee));
        companyRepository.save(savedCompany);

        Company updatedCompany = new Company(companyId, "oocl", Collections.emptyList());
        String requestJson = JSONObject.toJSONString(updatedCompany);

        //when
        String responseContentAsString = mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", companyId).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        //then
        JSONObject jsonObject = JSONArray.parseObject(responseContentAsString);
        List<Employee> employees = JSONArray.parseArray(jsonObject.get("employees").toString(), Employee.class);
        assertEquals(1, savedCompany.getEmployees().size());
        assertEquals(0, employees.size());
    }

    @Test
    void should_return_nothing_when_delete_company_given_company_id() throws Exception {
        Company company = new Company("oocl", null);
        Company savedCompany = companyRepository.save(company);
        int companyId = savedCompany.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", companyId))
                .andExpect(status().isOk());
    }
}