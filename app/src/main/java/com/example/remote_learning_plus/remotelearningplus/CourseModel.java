package com.example.remote_learning_plus.remotelearningplus;

public class CourseModel {
    private String courseID;
    private String courseSection;
    private String courseRef;

    public CourseModel(String courseID, String courseSection) {
        this.courseID = courseID;
        this.courseSection = courseSection;
        this.courseRef = courseRef;
    }

    public CourseModel() {
        // Empty constructor needed
    }

    public String getCourseID() {
        return courseID;
    }


    public String getCourseSection() {
        return courseSection;
    }

    public String getCourseRef(){ return courseRef;}
}
