package com.thoughtworks.springbootemployee.entity;

import java.util.List;

public class Company {
    private int id;
    private int employeesNums;
    private List<Employee> employees;
    private String companyName;

    public Company(int id, int employeesNums, List<Employee> employees, String companyName) {
        this.id = id;
        this.employeesNums = employeesNums;
        this.employees = employees;
        this.companyName = companyName;
    }

    public Company() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNums() {
        return employeesNums;
    }

    public void setEmployeesNums(int employeesNums) {
        this.employeesNums = employeesNums;
    }

    public int getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setId(int id) {
        this.id = id;
    }
}
