package com.thoughtworks.springbootemployee.integration;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegration {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;
    Company company;

    @BeforeEach
    void setData() {
        company = companyRepository.save(new Company("oocl", Collections.emptyList()));
    }

    @AfterEach
    void deleteDate() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_employees_when_find_all_employee_given_nothing() throws Exception {
        //given
        List<Employee> employees = employeeRepository.saveAll(asList(new Employee("jessie", 17, "female", 50000.0, company.getId()), new Employee("Nina", 17, "male", 50000.0, company.getId())));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(employees.size()));
    }

    @Test
    void should_return_employee_when_find_employee_by_id_given_employee_id() throws Exception {
        //given
        List<Employee> employees = employeeRepository.saveAll(asList(new Employee("jessie", 17, "female", 50000.0, company.getId()), new Employee("Nina", 17, "male", 50000.0, company.getId())));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employees.get(0).getId()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("jessie"));
    }

    @Test
    void should_return_employees_when_find_employee_by_page_given_page_and_pageSize() throws Exception {
        //given
        employeeRepository.saveAll(asList(new Employee("jessie", 17, "female", 50000.0, company.getId()), new Employee("Nina", 17, "male", 50000.0, company.getId())));
        int page = 0;
        int pageSize = 1;
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}", page, pageSize))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.length()").value("1")).andExpect(jsonPath("$.content.[0].name").value("jessie"));
    }

    @Test
    void should_return_employees_when_find_employee_by_gender_given_gender() throws Exception {
        //given
        List<Employee> employees = employeeRepository.saveAll(asList(new Employee("jessie", 17, "female", 50000.0, company.getId()), new Employee("Nina", 17, "male", 50000.0, company.getId())));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("gender","female"))
                .andExpect(jsonPath("$.length()").value(1)).andExpect(jsonPath("$.[0].name").value("jessie"));

    }

    @Test
    void should_return_employee_when_save_employee_given_employee() throws Exception {
        //given
        Employee employee = new Employee("jessie", 17, "female", 50000.0, company.getId());
        String jsonString = JSONObject.toJSONString(employee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.name").value("jessie"));
    }

    @Test
    void should_return_employee_when_update_employee_given_employee_id_and_updated_info() throws Exception {
        //given
        Employee employee = new Employee("jessie", 17, "female", 50000.0, company.getId());
        Employee savedEmployee = employeeRepository.save(employee);
        Employee updatedEmployee = new Employee(savedEmployee.getId(), "jessie", 18, "female", 100000.0, company.getId());
        String jsonString = JSONObject.toJSONString(updatedEmployee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", savedEmployee.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.name").value("jessie"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    void should_return_nothing_when_delete_employee_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee("jessie", 17, "female", 50000.0, company.getId());
        Employee savedEmployee = employeeRepository.save(employee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", savedEmployee.getId()))
                .andExpect(status().isOk());
    }
}
