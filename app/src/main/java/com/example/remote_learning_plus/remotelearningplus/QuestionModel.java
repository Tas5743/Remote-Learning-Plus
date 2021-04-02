package com.example.remote_learning_plus.remotelearningplus;

public class QuestionModel{
    private String question;
    private String student;

    public QuestionModel(String question, String student) {
        this.question = question;
        this.student = student;
    }

    public QuestionModel() {
        // Empty constructor needed
    }

    public String getQuestion() {
        return question;
    }


    public String getStudent() {
        return student;
    }
}