package com.example.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Set;
import com.anychart.enums.TooltipPositionMode;
import com.example.assignment12.databinding.FragmentVisualizeSleepQualityBinding;
import com.example.assignment12.models.LogEntry;
import com.example.assignment12.viewmodel.VisualizeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import com.anychart.chart.common.dataentry.DataEntry;

public class VisualizeSleepQualityFragment extends Fragment {

    private VisualizeViewModel viewModel;
    private Cartesian cartesianChart;
    private FragmentVisualizeSleepQualityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(VisualizeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVisualizeSleepQualityBinding.inflate(inflater, container, false);
        setupChart(binding.chartSleepQuality, "Sleep Quality Score", LogEntry::getSleepQualityScore, 0, 5);

        viewModel.getLogEntries().observe(getViewLifecycleOwner(), entries -> {
            getActivity().runOnUiThread(() -> updateChartData(entries, LogEntry::getSleepQualityScore));
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.back();
            }
        });

        return binding.getRoot();
    }

    private void setupChart(AnyChartView chartView, String title, Function<LogEntry, Number> valueExtractor, double min, double max) {
        cartesianChart = AnyChart.line();
        cartesianChart.animation(true);
        cartesianChart.title(title);

        cartesianChart.yScale().minimum(min).maximum(max);
        cartesianChart.yAxis(0).title("Value");
        cartesianChart.xAxis(0).labels().format("{%Value}{type:date, format:yyyy-MM-dd}");
        cartesianChart.xAxis(0).title("Date");

        cartesianChart.tooltip().positionMode(TooltipPositionMode.POINT);

        chartView.setChart(cartesianChart);
        Log.d("VisualizeSleepQualityFragment", "Chart setup for " + title);
    }

    private void updateChartData(List<LogEntry> entries, Function<LogEntry, Number> valueExtractor) {
        if (cartesianChart != null) {
            List<DataEntry> updatedData = new ArrayList<>();
            entries.forEach(entry -> {
                Number value = valueExtractor.apply(entry);
                if (value != null) {
                    updatedData.add(new CustomDataEntry(entry.getDateTime().toString(), value));
                    Log.d("VisualizeSleepQualityFragment", "Date: " + entry.getDateTime() + ", Value: " + value);
                } else {
                    Log.d("VisualizeSleepQualityFragment", "Null or invalid value for entry: " + entry);
                }
            });

            if (!updatedData.isEmpty()) {
                Set dataSet = Set.instantiate();
                dataSet.data(updatedData);
                cartesianChart.removeAllSeries();
                Line series = cartesianChart.line(dataSet.mapAs("{ x: 'x', value: 'value' }"));
                series.name("Sleep Quality Series");
                cartesianChart.draw(true);
                Log.d("VisualizeSleepQualityFragment", "Data updated");
            } else {
                Log.d("VisualizeSleepQualityFragment", "No data to display");
            }
        } else {
            Log.d("VisualizeSleepQualityFragment", "Cartesian object not found");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Clear the binding when the view is destroyed to prevent memory leaks
    }

    private static class CustomDataEntry extends DataEntry {
        CustomDataEntry(String x, Number value) {
            setValue("x", x);
            setValue("value", value);
        }
    }
    VisualizeSleepQualityFragmentListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof VisualizeSleepQualityFragmentListener) {
            mListner = (VisualizeSleepQualityFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement VisualizeSleepQualityFragmentListener");
        }
    }

    public interface VisualizeSleepQualityFragmentListener {
        void back();
    }
}
