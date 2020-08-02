package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private CompanyRepository companyRepository;

    @BeforeEach
    void addCompany() {
        companyRepository.save(new Company(1, "oocl", Collections.emptyList()));
        when(companyRepository.existsById(1)).thenReturn(true);
    }

    @Test
    void should_return_employees_when_get_employees_given_nothing() {
        //given
        when(employeeRepository.findAll()).thenReturn(asList(new Employee(1, "oocl1", 18, "female", 10000.0, 1), new Employee(2, "oocl2", 18, "female", 10000.0, 1)));
        //when
        List<Employee> employees = employeeService.getAllEmployees();
        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employee_when_get_employees_by_id_given_employee_id() {
        //given
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "oocl1", 18, "female", 10000.0, 1)));
        //when
        Employee employee = employeeService.getEmployeeByID(1);
        //then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        Employee employee = new Employee(1, "oocl1", 18, "female", 10000.0, 1);
        when(employeeRepository.save(employee)).thenReturn(employee);
        //when
        Employee savedEmployee = employeeService.addEmployee(employee);
        //then
        assertEquals(1, savedEmployee.getId());
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() {
        //given
        when(employeeRepository.findByGender("female")).thenReturn(asList(new Employee(1, "oocl1", 18, "female", 10000.0)));
        //when
        List<Employee> employees = employeeService.getEmployeesByGender("female");
        //then
        assertEquals(1, employees.size());
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() {
        //given
        Employee employee = new Employee(1, "oocl1", 18, "female", 10000.0, 1);
        when(employeeRepository.save(employee)).thenReturn(employee);
        employeeRepository.save(employee);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        //when
        Employee updatedEmployee = employeeService.updateEmployee(1, new Employee(1, "oocl1", 20, "female", 20000.0, 1));
        //then
        assertEquals(1, updatedEmployee.getId());
        assertEquals(20, updatedEmployee.getAge());
    }

    @Test
    void should_return_null_when_update_given_employee_id_is_not_found_and_employee_info() {
        //given
        when(employeeRepository.findById(4)).thenReturn(null);
        //when
        Employee updatedEmployee = employeeService.updateEmployee(4, new Employee(4, "oocl1", 20, "female", 20000.0));
        //then
        assertNull(updatedEmployee);
    }

    @Test
    void should_return_employee_when_delete_given_employee_id() {
        //given
        Employee employee = new Employee(1, "oocl1", 18, "female", 10000.0, 1);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        int employee_id = savedEmployee.getId();
        when(employeeRepository.existsById(employee_id)).thenReturn(true);
        //when
        employeeService.deleteEmployee(employee_id);
        //then
        verify(employeeRepository).deleteById(1);
    }

    @Test
    void should_return_employees_when_get_employees_by_page_given_pageNum_and_pageSize() {
        //given
        List<Employee> employees = asList(new Employee(1, "alibaba1", 20, "male", 6000.0), new Employee(2, "alibaba2", 19, "male", 8000.0));
        when(employeeRepository.saveAll(employees)).thenReturn(employees);
        employeeRepository.saveAll(employees);
        when(employeeRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(asList(new Employee(1, "alibaba1", 20, "male", 6000.0))));
        int pageNum = 0;
        int pageSize = 1;
        //when
        Page employeePage = employeeService.getEmployeeByPage(pageNum, pageSize);
        //then
        assertEquals(1, employeePage.getContent().size());
        assertEquals(1, ((Employee) employeePage.getContent().get(0)).getId());
    }
}
