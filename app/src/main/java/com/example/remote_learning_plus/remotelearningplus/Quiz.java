package com.example.remote_learning_plus.remotelearningplus;

import java.util.HashMap;

public class Quiz {

    private String title;
    private int items;
    private HashMap<String, Boolean> tags;
    private HashMap<String, Double> scores;


    public Quiz() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public HashMap<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, Boolean> tags) {
        this.tags = tags;
    }

    public HashMap<String, Double> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, Double> scores) {
        this.scores = scores;
    }


}
