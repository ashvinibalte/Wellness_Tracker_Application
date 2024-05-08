package com.example.assignment12.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LogEntryDao {
    // Asynchronous method to fetch all log entries for LiveData observation
    @Query("SELECT * FROM log_entries")
    LiveData<List<LogEntry>> getAll();

    // Synchronous method to fetch all log entries directly
    @Query("SELECT * FROM log_entries")
    List<LogEntry> getAllSync();

    @Insert
    void insertAll(LogEntry... wellnessTracker);

    @Delete
    void delete(LogEntry wellnessTracker);
}
