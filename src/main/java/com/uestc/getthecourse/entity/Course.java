package com.uestc.getthecourse.entity;

import java.sql.Timestamp;

public class Course {
    private String courseId;
    private String courseName;
    private String teacherName;
    private String openCollege;
    private String openCampus;
    private int classHours;
    private int credit;
    private Timestamp startTime;
    private Timestamp endTime;
    private int maxCapacity;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOpenCollege() {
        return openCollege;
    }

    public void setOpenCollege(String openCollege) {
        this.openCollege = openCollege;
    }

    public String getOpenCampus() {
        return openCampus;
    }

    public void setOpenCampus(String openCampus) {
        this.openCampus = openCampus;
    }

    public int getClassHours() {
        return classHours;
    }

    public void setClassHours(int classHours) {
        this.classHours = classHours;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", openCollege='" + openCollege + '\'' +
                ", openCampus='" + openCampus + '\'' +
                ", classHours=" + classHours +
                ", credit=" + credit +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
