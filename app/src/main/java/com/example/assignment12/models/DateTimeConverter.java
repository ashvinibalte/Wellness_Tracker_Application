package com.example.assignment12.models;

import androidx.room.TypeConverter;
import java.util.Locale;

public class DateTimeConverter {
    @TypeConverter
    public static String fromDateTime(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return String.format(Locale.US, "%d-%d-%d %d:%d", dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute());
    }

    @TypeConverter
    public static DateTime toDateTime(String dateTimeString) {
        if (dateTimeString == null) {
            return null;
        }
        String[] parts = dateTimeString.split("[-: ]");
        if (parts.length == 5) {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            int hour = Integer.parseInt(parts[3]);
            int minute = Integer.parseInt(parts[4]);
            return new DateTime(year, month, day, hour, minute);
        }
        return null;
    }
}
