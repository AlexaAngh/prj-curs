package com.itfactory.model;

public class Student {

    int studentId;
    String studentName;
    double budget;


    public Student(int studentId, String studentName, double budget) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.budget = budget;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override public String toString()
    {
        return studentId + "," + studentName + "," + budget;
    }
}
