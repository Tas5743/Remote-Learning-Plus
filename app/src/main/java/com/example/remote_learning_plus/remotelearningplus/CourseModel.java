package com.teamremote.remotelearningplus;

public class CourseModel {
    private String level;
    private String subject;
    private String section;

    public CourseModel(String level, String subject, String section) {
        this.level = level;
        this.subject = subject;
        this.section = section;
    }

    public CourseModel() {
        // Empty constructor needed
    }

    public String getLevel() {
        return level;
    }

    public String getSubject() {
        return subject;
    }

    public String getSection() {
        return section;
    }
}
