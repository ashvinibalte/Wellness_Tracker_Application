package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment12.R;
import com.example.assignment12.databinding.FragmentMainBinding;
import com.example.assignment12.databinding.LogRowItemBinding;
import com.example.assignment12.models.AppDatabase;
import com.example.assignment12.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment"; // Tag for logging
    FragmentMainBinding binding;
    ArrayList<LogEntry> mLogs = new ArrayList<>();
    MainAdapter adapter;
    AppDatabase db;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Main");
        adapter = new MainAdapter();
        binding.recyclerViewLogs.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewLogs.setAdapter(adapter);
        binding.buttonAddLog.setOnClickListener(v -> mListner.gotoAddLog());
        binding.buttonVisualize.setOnClickListener(v -> mListner.gotoVisualize());
        binding.buttonGraphSeephrs.setOnClickListener(v -> mListner.GraphSleephr());
        binding.buttonGraphExerciseHr.setOnClickListener(v -> mListner.GraphExercisehr());
        binding.buttonGraphQuality.setOnClickListener(v -> mListner.GraphSleepQuality());

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "wellnessTracker-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        loadAndDisplayData();
    }

    private void loadAndDisplayData() {
        new Thread(() -> {
            List<LogEntry> logs = db.wellnessTrackerDao().getAllSync();  // Fetch data synchronously
            getActivity().runOnUiThread(() -> {
                mLogs.clear();
                mLogs.addAll(logs);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Data fetched: " + logs.size() + " entries");
                for (LogEntry log : logs) {
                    Log.d(TAG, "LogEntry: " + log.toString());
                }
            });
        }).start();
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LogRowItemBinding Logbinding = LogRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MainViewHolder(Logbinding);
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            LogEntry log = mLogs.get(position);
            holder.setupUI(log);
        }

        @Override
        public int getItemCount() {
            return mLogs.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {
            LogRowItemBinding mBinding;

            public MainViewHolder(@NonNull LogRowItemBinding Logbinding) {
                super(Logbinding.getRoot());
                this.mBinding = Logbinding;
            }

            public void setupUI(LogEntry klog) {
                mBinding.textViewDateTime.setText("Date & Time: " + klog.getDateTime().toString());
                mBinding.textViewSleepHours.setText("SleepHour: " + klog.getSleepHour());
                mBinding.textViewSleepQuality.setText("SleepQuality: " + klog.getSleepQuality().getQuality());
                mBinding.textViewExerciseHours.setText("ExerciseHour: " + klog.getExerciseHour().getHours() + " hours");
                mBinding.textViewWeight.setText("Weight: " + klog.getWeight() + " lbs");
                mBinding.imageViewDelete.setOnClickListener(v -> {
                    db.wellnessTrackerDao().delete(klog);
                    loadAndDisplayData();
                });
            }
        }
    }

    MainFragmentListner mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (MainFragmentListner) context;
    }

    public interface MainFragmentListner {
        void gotoAddLog();
        void gotoVisualize();
        void GraphSleephr();
        void GraphExercisehr();
        void GraphWeight();
        void GraphSleepQuality();
    }
}
