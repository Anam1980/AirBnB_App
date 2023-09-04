package com.example.Airbnb.model;
public class User {

    private int aadharCardNo; //This is the unique key that determines a unique user
    private String name;
    private int age;

    public User(int aadharCardNo, String name, int age) {
        this.aadharCardNo = aadharCardNo;
        this.name = name;
        this.age = age;
    }

    public int getaadharCardNo() {
        return aadharCardNo;
    }

    public void setaadharCardNo(int aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

