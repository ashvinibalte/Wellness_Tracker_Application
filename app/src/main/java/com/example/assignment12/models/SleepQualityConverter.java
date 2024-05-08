package com.example.assignment12.models;

import androidx.room.TypeConverter;

public class SleepQualityConverter {
    @TypeConverter
    public static String fromSleepQuality(SleepQuality sleepQuality) {
        return sleepQuality == null ? null : sleepQuality.getQuality();
    }

    @TypeConverter
    public static SleepQuality toSleepQuality(String quality) {
        return quality == null ? null : new SleepQuality(quality, determineScore(quality));
    }

    private static int determineScore(String quality) {
        switch (quality) {
            case "Excellent":
                return 5;
            case "Very Good":
                return 4;
            case "Good":
                return 3;
            case "Fair":
                return 2;
            case "Poor":
                return 1;
            default:
                return 0;  // or throw an IllegalArgumentException if the quality is unknown
        }
    }
}

