package com.example.assignment12.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.assignment12.models.AppDatabase;
import com.example.assignment12.models.LogEntry;
import java.util.List;


import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.assignment12.models.AppDatabase;
import com.example.assignment12.models.LogEntry;

import java.util.List;

public class LogEntryRepository {
    private LiveData<List<LogEntry>> logEntries;
    private AppDatabase db;

    public LogEntryRepository(Application application) {
        db = Room.databaseBuilder(application.getApplicationContext(),
                        AppDatabase.class, "wellnessTracker-db")
                .fallbackToDestructiveMigration()
                .build();
        logEntries = db.wellnessTrackerDao().getAll();
    }

    public LiveData<List<LogEntry>> getLogEntries() {
        return logEntries;
    }
}

