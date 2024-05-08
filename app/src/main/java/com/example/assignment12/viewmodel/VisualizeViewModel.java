package com.example.assignment12.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.assignment12.models.LogEntry;
import com.example.assignment12.repository.LogEntryRepository;
import java.util.List;

public class VisualizeViewModel extends AndroidViewModel {
    private LiveData<List<LogEntry>> logEntries;
    private LogEntryRepository repository;

    public VisualizeViewModel(Application application) {
        super(application);
        repository = new LogEntryRepository(application);
        logEntries = repository.getLogEntries();
        logEntries.observeForever(entries -> {
            if (entries == null || entries.isEmpty()) {
                Log.d("VisualizeViewModel", "No log entries fetched.");
            } else {
                Log.d("VisualizeViewModel", "Fetched " + entries.size() + " log entries.");
                // Example to log specific data, adapt field names and methods according to your LogEntry model
                for (LogEntry entry : entries) {
                    Log.d("VisualizeViewModel", "Entry: " + entry.toString());
                }
            }
        });
    }

    public LiveData<List<LogEntry>> getLogEntries() {
        return logEntries;
    }
}
