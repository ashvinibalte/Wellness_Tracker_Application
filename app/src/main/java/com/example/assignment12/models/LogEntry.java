package com.example.assignment12.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "log_entries")
public class LogEntry {
    @PrimaryKey(autoGenerate = true)
    public int cid;

    private DateTime dateTime;
    private SleepQuality sleepQuality;
    private SleepHour sleepHour;
    private ExerciseHour exerciseHour;
    private double weight;

    // Default constructor
    public LogEntry() {
    }

    // Constructor with all parameters
    public LogEntry(DateTime dateTime, SleepHour sleepHour, SleepQuality sleepQuality, ExerciseHour exerciseHour, double weight) {
        this.dateTime = dateTime;
        this.sleepHour = sleepHour;
        this.sleepQuality = sleepQuality;
        this.exerciseHour = exerciseHour;
        this.weight = weight;
    }

    // Getter and setter for cid
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    // Getter and setter for dateTime
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Getter and setter for sleepQuality
    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(SleepQuality sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    // Getter and setter for sleepHour
    public SleepHour getSleepHour() {
        return sleepHour;
    }

    public void setSleepHour(SleepHour sleepHour) {
        if (sleepHour != null && sleepHour.getHours() >= 0) {
            this.sleepHour = sleepHour;
        } else {
            throw new IllegalArgumentException("Sleep hours cannot be negative");
        }
    }

    // Getter and setter for exerciseHour
    public ExerciseHour getExerciseHour() {
        return exerciseHour;
    }

    public void setExerciseHour(ExerciseHour exerciseHour) {
        if (exerciseHour != null && exerciseHour.getHours() >= 0) {
            this.exerciseHour = exerciseHour;
        } else {
            throw new IllegalArgumentException("Exercise hours cannot be negative");
        }
    }

    // Getter and setter for weight
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight >= 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
    }

    // Additional methods to extract numeric values directly
    public double getSleepHourHours() {
        return sleepHour != null ? sleepHour.getHours() : 0;
    }

    public int getSleepQualityScore() {
        return sleepQuality != null ? sleepQuality.getScore() : 0;
    }

    public double getExerciseHourHours() {
        return exerciseHour != null ? exerciseHour.getHours() : 0;
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "LogEntry{" +
                "cid=" + cid +
                ", dateTime=" + dateTime +
                ", sleepQuality=" + sleepQuality +
                ", sleepHour=" + sleepHour +
                ", exerciseHour=" + exerciseHour +
                ", weight=" + weight +
                '}';
    }
}
