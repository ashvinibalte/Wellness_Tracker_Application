package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import com.example.assignment12.R;
import com.example.assignment12.databinding.FragmentQualitySleepBinding;
import com.example.assignment12.models.SleepQuality;

public class QualitySleepFragment extends Fragment {

    private FragmentQualitySleepBinding binding;
    private ArrayList<SleepQuality> qualities;
    private ArrayAdapter<SleepQuality> adapter;

    public QualitySleepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeSleepQualities();
    }

    private void initializeSleepQualities() {
        qualities = new ArrayList<>();
        qualities.add(new SleepQuality("Excellent", 5));
        qualities.add(new SleepQuality("Very Good", 4));
        qualities.add(new SleepQuality("Good", 3));
        qualities.add(new SleepQuality("Fair", 2));
        qualities.add(new SleepQuality("Poor", 1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualitySleepBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Sleep Quality");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, qualities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SleepQuality selectedQuality = qualities.get(position);
                mListener.onSleepQualitySelected(selectedQuality);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cancel_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cancel) {
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private QualitySleepListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (QualitySleepListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement QualitySleepListener");
        }
    }

    public interface QualitySleepListener {
        void onSleepQualitySelected(SleepQuality quality);
        void onSelectionCanceled();
    }
}
