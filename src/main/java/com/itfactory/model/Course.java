package com.itfactory.model;

import java.time.LocalDate;

public class Course {

    int courseID;
    String courseName;
    double price;
    LocalDate startDate;

    public Course(int courseID, String courseName, double price) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override public String toString()
    {
        return courseID + "," + courseName + "," + price + "," + startDate;
    }
}
