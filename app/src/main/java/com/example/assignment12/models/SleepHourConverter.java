package com.example.assignment12.models;

import androidx.room.TypeConverter;

public class SleepHourConverter {
    @TypeConverter
    public static Double fromSleepHour(SleepHour sleepHour) {
        if (sleepHour == null) {
            return null;
        }
        return sleepHour.getHours();
    }

    @TypeConverter
    public static SleepHour toSleepHour(Double hours) {
        if (hours == null) {
            return null;
        }
        return new SleepHour(hours);
    }
}

