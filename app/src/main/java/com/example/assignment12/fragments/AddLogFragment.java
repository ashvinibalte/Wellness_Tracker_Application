package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.assignment12.databinding.FragmentAddLogBinding;
import com.example.assignment12.models.AppDatabase;
import com.example.assignment12.models.DateTime;
import com.example.assignment12.models.ExerciseHour;
import com.example.assignment12.models.LogEntry;
import com.example.assignment12.models.SleepHour;
import com.example.assignment12.models.SleepQuality;

public class AddLogFragment extends Fragment {

    public SleepQuality selectedSleepQuality;
    public SleepHour selectedSleepHour;
    public ExerciseHour selectedExerciseHour;
    public DateTime selectedDateTime;

    private FragmentAddLogBinding binding;


    public AddLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Log");
        if (selectedDateTime != null) {
            binding.textViewDateTime.setText(String.format("%d-%02d-%02d %02d:%02d",
                    selectedDateTime.getYear(), selectedDateTime.getMonth() + 1,
                    selectedDateTime.getDay(), selectedDateTime.getHour(),
                    selectedDateTime.getMinute()));
        }
        if (selectedSleepQuality != null) {
            binding.textViewSleepQuality.setText(selectedSleepQuality.getQuality());

        }
        if (selectedSleepHour != null) {
            binding.textViewSleepHours.setText(String.format("%.1f hours", selectedSleepHour.getHours()));
        }
        if (selectedExerciseHour != null) {
            binding.textViewExerciseHours.setText(String.format("%.1f hours", selectedExerciseHour.getHours()));
        }

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightText = binding.editTextWeight.getText().toString().trim();
                if (selectedDateTime == null) {
                    Toast.makeText(getContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                } else if (selectedSleepQuality == null) {
                    Toast.makeText(getContext(), "Please select sleep quality", Toast.LENGTH_SHORT).show();
                } else if (selectedSleepHour == null) {
                    Toast.makeText(getContext(), "Please select sleep hours", Toast.LENGTH_SHORT).show();
                } else if (selectedExerciseHour == null) {
                    Toast.makeText(getContext(), "Please select exercise hours", Toast.LENGTH_SHORT).show();
                } else if (weightText.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter weight", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double weight = Double.parseDouble(weightText);
                        if (weight <= 0) {
                            Toast.makeText(getContext(), "Please enter a valid weight", Toast.LENGTH_SHORT).show();
                        } else {
                            AppDatabase db= Room.databaseBuilder(getActivity(),AppDatabase.class,"wellnessTracker-db")
                                    .fallbackToDestructiveMigration()
                                    .allowMainThreadQueries()
                                    .build();
                            LogEntry logEntry = new LogEntry(selectedDateTime, selectedSleepHour, selectedSleepQuality,  selectedExerciseHour, weight);
                            db.wellnessTrackerDao().insertAll(logEntry);
                            mListener.submit();

                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid weight entered", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.buttonCancel.setOnClickListener(v -> mListener.cancel());
        binding.buttonDateTime.setOnClickListener(v -> mListener.selectDateTime());
        binding.buttonExerciseHours.setOnClickListener(v -> mListener.selectExercise());
        binding.buttonSleepQuality.setOnClickListener(v -> mListener.selectQualitySleep());
        binding.buttonSleepHours.setOnClickListener(v -> mListener.selectSleep());
    }
    private AddLogFragmentListner  mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (AddLogFragmentListner ) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddLogFragmentListener");
        }
    }

    public interface AddLogFragmentListner  {
        void selectSleep();
        void selectQualitySleep();
        void selectExercise();
        void selectDateTime();
        void cancel();
        void submit();
    }
}