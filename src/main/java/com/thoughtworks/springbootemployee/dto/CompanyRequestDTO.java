package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyRequestDTO {
    private String companyName;
    private List<Employee> employees;

    public CompanyRequestDTO() {
    }

    public CompanyRequestDTO(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
