package com.example.assignment12.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment12.models.LogEntry;
import com.example.assignment12.repository.LogEntryRepository;

import java.util.List;
public class LogEntryViewModel extends ViewModel {
    public LogEntryViewModel() {
        // Default constructor
    }

    private LiveData<List<LogEntry>> logEntries;
    private LogEntryRepository repository;

    public LogEntryViewModel(Application application) {
        this.repository = new LogEntryRepository(application);
        logEntries = repository.getLogEntries(); // Make sure repository is initialized first
    }

    public LiveData<List<LogEntry>> getLogEntries() {
        return logEntries;
    }
}
