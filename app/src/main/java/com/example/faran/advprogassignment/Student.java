package com.example.faran.advprogassignment;

import java.io.Serializable;

/**
 * Created by Faran on 28/03/2018.
 */

public class Student extends Person implements Serializable {

    //Class members
    private String studentNo;
    private String courseTitle;
    private String startDate;
    private String bursary;
    private String email;

    //Constructor for a student - grab some details from the Person superclass
    public Student(String fullName, String gender, String DOB, String address, String postCode, String studentNo, String courseTitle, String startDate, String bursary, String email)
    {
        super(fullName, gender, DOB, address, postCode);
        this.studentNo = studentNo;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.bursary = bursary;
        this.email = email;
    }

    //Getters and setters for class members
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBursary() {
        return bursary;
    }

    public void setBursary(String bursary) {
        this.bursary = bursary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //String representation of Student object
    public String toString() {
        return "Full name:" + super.getFullName() + ",, "
                + "Gender:" + super.getGender() + ",, " + "Date of birth:"
                + super.getDOB() + ",, " + "Address:" + super.getAddress() + ",, "
                + "Post code:" + super.getPostCode() + ",, " + "Student number:" + this.getStudentNo()
                + ",, " + "Course title:" + this.getCourseTitle() + ",, " + "Start date:" + this.getStartDate()
                + "Bursary:" + this.getBursary() + ",, " + "Email:" + this.getEmail();
    }
}
