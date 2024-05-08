package com.example.assignment12.models;

public class SleepQuality {
    private String quality;
    private int score;

    public SleepQuality(String quality, int score) {
        this.quality = quality;
        this.score = score;
    }

    public String getQuality() {
        return quality;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return quality; // This will be displayed in the ListView
    }
}
