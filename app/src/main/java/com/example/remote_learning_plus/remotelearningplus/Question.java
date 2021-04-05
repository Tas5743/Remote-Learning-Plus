package com.example.remote_learning_plus.remotelearningplus;

import java.util.HashMap;

public class Question {
    private String question;
    private int itemNum;
    private HashMap<String, String> choices;
    private int key;
    private int point;
    private int timeLimit;

    public Question() {

    }


    public int getItemNum() { return itemNum; }

    public void setItemNum(int itemNum) { this.itemNum = itemNum; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public HashMap<String, String> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<String, String> choices) { this.choices = choices; }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }




}
