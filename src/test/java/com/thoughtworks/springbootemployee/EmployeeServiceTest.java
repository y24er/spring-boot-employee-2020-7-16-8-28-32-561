package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_return_employees_when_get_employees_given_nothing() {
        //given
        when(employeeRepository.findAll()).thenReturn(asList(new Employee(1, "oocl1", 18, "female", 10000.0), new Employee(2, "oocl2", 18, "female", 10000.0)));
        //when
        List<Employee> employees = employeeService.getAllEmployees();
        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employee_when_get_employees_by_id_given_employee_id() {
        //given
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "oocl1", 18, "female", 10000.0)));
        //when
        Employee employee = employeeService.getEmployeeByID(1);
        //then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        when(employeeRepository.save(any())).thenReturn(new Employee(1, "oocl1", 18, "female", 10000.0));
        //when
        Employee employee = employeeService.addEmployee(new Employee(1, "oocl1", 18, "female", 10000.0));
        //then
        assertEquals(1, employee.getId());
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
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "oocl1", 18, "female", 10000.0)));
        //when
        Employee updatedEmployee = employeeService.updateEmployee(1, new Employee(1, "oocl1", 20, "female", 20000.0));
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
        int employee_id = 1;
        //when
        String result = employeeService.deleteEmployee(employee_id);
        //then
        assertEquals("delete success", result);
        verify(employeeRepository).deleteById(1);

    }
}
