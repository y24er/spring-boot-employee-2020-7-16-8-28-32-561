package com.thoughtworks.springbootemployee.dto;

import java.util.List;

public class CompanyResponseDTO {
    private int id;
    private String companyName;
    private List<String> employeesName;

    public CompanyResponseDTO() {
    }

    public CompanyResponseDTO(int id, String companyName, List<String> employeesName) {
        this.id = id;
        this.companyName = companyName;
        this.employeesName = employeesName;
    }

    public CompanyResponseDTO(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployeesName(List<String> employeesName) {
        this.employeesName = employeesName;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<String> getEmployeesName() {
        return employeesName;
    }
}
