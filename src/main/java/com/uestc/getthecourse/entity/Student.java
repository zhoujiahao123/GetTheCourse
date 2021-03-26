package com.uestc.getthecourse.entity;

public class Student {
    private String studentId;
    private String password;
    private String slat;
    private String classes;
    private String grade;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + slat + '\'' +
                ", classes='" + classes + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }
}
