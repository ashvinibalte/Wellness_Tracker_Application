package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.assignment12.databinding.FragmentSleepBinding;
import com.example.assignment12.models.SleepHour;

public class SleepFragment extends Fragment {

    private ArrayList<SleepHour> sleepHours = new ArrayList<>();
    private ArrayAdapter<SleepHour> adapter;
    private FragmentSleepBinding binding;


    public SleepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fillSleepHours();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d("MenuTest", "Inflating menu");
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

    private void fillSleepHours() {
        for (double i = 0.5; i <= 15; i += 0.5) {
            sleepHours.add(new SleepHour(i));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSleepBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Sleep Hours");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, sleepHours);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SleepHour selectedHour = sleepHours.get(position);
                mListener.onSleepSelected(selectedHour);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }




    SleepListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Lifecycle", "Fragment attached");
        try {
            mListener = (SleepListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SleepListener");
        }
    }

    public interface SleepListener {
        void onSleepSelected(SleepHour sleepHour);
        void onSelectionCanceled();
        void cancel();
    }
}
