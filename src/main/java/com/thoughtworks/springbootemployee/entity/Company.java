package com.thoughtworks.springbootemployee.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int employeesNums;
    @OneToMany
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

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
