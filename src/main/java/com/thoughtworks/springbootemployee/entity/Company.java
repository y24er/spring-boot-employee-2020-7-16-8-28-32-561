package com.thoughtworks.springbootemployee.entity;

import java.util.List;

public class Company {
    private int id;
    private List<Employee> employees;

    public Company(int id) {
        this.id = id;
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
