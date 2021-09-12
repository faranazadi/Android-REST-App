package com.example.faran.advprogassignment;

import java.io.Serializable;

/**
 * Created by Faran on 28/03/2018.
 */

public class Person implements Serializable {

    //Class members
    private String fullName;
    private String gender;
    private String DOB;
    private String address;
    private String postCode;

    //Constructor for a person
    public Person(String fullName, String gender, String DOB, String address, String postCode)
    {
        this.fullName = fullName;
        this.gender = gender;
        this.DOB = DOB;
        this.address = address;
        this.postCode = postCode;
    }

    //Getters and setters for the class members
    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    //String representation of Person object
    public String toString() {
        return "Full name:" + getFullName() + ",, "
                + "Gender:" + getGender() + ",, " + "Date of birth:"
                + getDOB() + ",, " + "Address:" + getAddress() + ",, "
                + "Post code:" + getPostCode();
    }
}
