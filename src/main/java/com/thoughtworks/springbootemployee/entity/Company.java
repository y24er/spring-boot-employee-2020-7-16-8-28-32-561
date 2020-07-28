package com.thoughtworks.springbootemployee.entity;

public class Company {
    private int id;

    public Company(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
