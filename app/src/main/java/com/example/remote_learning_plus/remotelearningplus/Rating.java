package com.example.remote_learning_plus.remotelearningplus;

import java.util.ArrayList;
import java.util.HashMap;

public class Rating {
    public HashMap<String, Object> getComprehension() {
        return comprehension;
    }

    public void setComprehension(HashMap<String, Object> comprehension) {
        this.comprehension = comprehension;
    }

    public HashMap<String, Object> getEngagement() {
        return engagement;
    }

    public void setEngagement(HashMap<String, Object> engagement) {
        this.engagement = engagement;
    }

    public HashMap<String, Object> getPace() {
        return pace;
    }

    public void setPace(HashMap<String, Object> pace) {
        this.pace = pace;
    }

    private HashMap<String, Object> comprehension;
    private HashMap<String, Object> engagement;
    private HashMap<String, Object> pace;






}
