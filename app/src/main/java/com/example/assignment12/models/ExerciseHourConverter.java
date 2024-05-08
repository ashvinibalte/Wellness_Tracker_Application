package com.example.assignment12.models;



import androidx.room.TypeConverter;

public class ExerciseHourConverter {
    @TypeConverter
    public static Double fromExerciseHour(ExerciseHour exerciseHour) {
        if (exerciseHour == null) {
            return null;
        }
        return exerciseHour.getHours();
    }

    @TypeConverter
    public static ExerciseHour toExerciseHour(Double hours) {
        if (hours == null) {
            return null;
        }
        return new ExerciseHour(hours);
    }
}

