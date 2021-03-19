package com.example.remote_learning_plus.remotelearningplus;

public class CourseModel {
    private String courseID;
    private String courseSection;

    public CourseModel(String courseID, String courseSection) {
        this.courseID = courseID;
        this.courseSection = courseSection;
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
}
