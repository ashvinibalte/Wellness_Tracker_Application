package com.example.assignment12.models;

public class SleepHour {
    private double hours;
    private String description; // Optional, for future use

    public SleepHour(double hours) {
        this.hours = hours;
        this.description = formatDescription(hours);
    }

    private String formatDescription(double hours) {
        // This method could be used to provide a more descriptive text if needed
        if (hours == 0.5) {
            return "Half an hour";
        } else {
            return hours + " hours";
        }
    }

    public double getHours() {
        return hours;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        // This will determine what is displayed in the ArrayAdapter
        return description;
    }
}
