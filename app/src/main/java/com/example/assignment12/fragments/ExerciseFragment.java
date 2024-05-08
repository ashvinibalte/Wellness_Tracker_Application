package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import com.example.assignment12.R;
import com.example.assignment12.databinding.FragmentExerciseBinding;
import com.example.assignment12.models.ExerciseHour;

public class ExerciseFragment extends Fragment {

    private FragmentExerciseBinding binding;
    private ArrayList<ExerciseHour> exerciseHours = new ArrayList<>();
    private ArrayAdapter<ExerciseHour> adapter;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fillExerciseHours();
    }

    private void fillExerciseHours() {
        for (double i = 0.5; i <= 15; i += 0.5) {
            exerciseHours.add(new ExerciseHour(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Exercise Hours");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exerciseHours);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseHour selectedHour = exerciseHours.get(position);
                mListener.onExerciseSelected(selectedHour);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cancel) {
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ExerciseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ExerciseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExerciseListener");
        }
    }

    public interface ExerciseListener {
        void onExerciseSelected(ExerciseHour hour);
        void onSelectionCanceled();
    }
}
