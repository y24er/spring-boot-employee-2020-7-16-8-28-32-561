package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootEmployeeApplicationTests {
	@Autowired
	CompanyService companyService;
	@Test
	void contextLoads() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "alibaba1", 20, "male", 6000.0));
		employees.add(new Employee(2, "alibaba2", 19, "male", 8000.0));
		companyService.addCompany(new Company(1, 2, employees, "alibaba"));
		System.out.println(companyService.getCompanies());
	}

}
