package com.example.assignment12.models;

public class ExerciseHour {
    private double hours;
    private String description;

    public ExerciseHour(double hours) {
        this.hours = hours;
        this.description = formatDescription(hours);
    }

    private String formatDescription(double hours) {
        return hours == 0.5 ? "Half an hour" : hours + " hours";
    }

    public double getHours() {
        return hours;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;  // This will be shown in the ListView
    }
}
