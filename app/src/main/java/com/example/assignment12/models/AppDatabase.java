package com.example.assignment12.models;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {LogEntry.class}, version = 2, exportSchema = false)
@TypeConverters({DateTimeConverter.class, SleepQualityConverter.class, ExerciseHourConverter.class, SleepHourConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    private static final String TAG = "AppDatabase";

    public abstract LogEntryDao wellnessTrackerDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "Creating new database instance");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "wellnessTracker.db")
                            .fallbackToDestructiveMigration()
                            .build();
                } else {
                    Log.d(TAG, "Database instance already exists");
                }
            }
        } else {
            Log.d(TAG, "Using existing database instance");
        }
        return INSTANCE;
    }
}
