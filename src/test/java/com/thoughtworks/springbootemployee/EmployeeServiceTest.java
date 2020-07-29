package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


public class EmployeeServiceTest {
    @Test
    void should_return_employees_when_get_employees_given_nothing() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        given(employeeRepository.getEmployees()).willReturn(asList(new Employee(1, "oocl1", 18, "female", 1000.0), new Employee(2, "oocl2", 18, "female", 1000.0)));

        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        List<Employee> employees = employeeService.getEmployees();
        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employee_when_get_employees_by_id_given_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        given(employeeRepository.getEmployee(1)).willReturn(new Employee(1, "oocl1", 18, "female", 1000.0));

        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        Employee employee = employeeService.getEmployeeByID(1);

        //then
        assertEquals(1, employee.getId());
    }
}
