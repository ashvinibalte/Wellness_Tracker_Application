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
import com.example.assignment12.R;
import com.example.assignment12.databinding.FragmentVisualizeBinding;
import com.example.assignment12.models.LogEntry;
import com.example.assignment12.viewmodel.VisualizeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import com.anychart.chart.common.dataentry.DataEntry;

public class VisualizeFragment extends Fragment {

    private VisualizeViewModel viewModel;
    private Map<Integer, Cartesian> charts = new HashMap<>();
    private Map<Integer, String> chartTitles = new HashMap<>();
    private FragmentVisualizeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(VisualizeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVisualizeBinding.inflate(inflater, container, false);
        setupChart(binding.chartWeight, "Weight", LogEntry::getWeight, 0, 500);

        viewModel.getLogEntries().observe(getViewLifecycleOwner(), entries -> {
            getActivity().runOnUiThread(() -> updateChartData(R.id.chart_weight, entries, LogEntry::getWeight));
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
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.title(title);

        cartesian.yScale().minimum(min).maximum(max);
        cartesian.yAxis(0).title("Value");
        cartesian.xAxis(0).labels().format("{%Value}{type:date, format:yyyy-MM-dd}");
        cartesian.xAxis(0).title("Date");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        chartView.setChart(cartesian);
        charts.put(chartView.getId(), cartesian);
        chartTitles.put(chartView.getId(), title);

        Log.d("VisualizeFragment", "Chart setup for " + title);
    }

    private void updateChartData(int chartViewId, List<LogEntry> entries, Function<LogEntry, Number> valueExtractor) {
        Cartesian cartesian = charts.get(chartViewId);
        if (cartesian != null) {
            List<DataEntry> updatedData = new ArrayList<>();
            entries.forEach(entry -> {
                Number value = valueExtractor.apply(entry);
                if (value != null) {
                    updatedData.add(new CustomDataEntry(entry.getDateTime().toString(), value));
                    Log.d("VisualizeFragment", "Chart ID: " + chartViewId + ", Date: " + entry.getDateTime() + ", Value: " + value);
                } else {
                    Log.d("VisualizeFragment", "Null or invalid value for Chart ID: " + chartViewId + ", Entry: " + entry);
                }
            });

            if (!updatedData.isEmpty()) {
                Set dataSet = Set.instantiate();
                dataSet.data(updatedData);
                cartesian.removeAllSeries();
                Line series = cartesian.line(dataSet.mapAs("{ x: 'x', value: 'value' }"));
                series.name(chartTitles.get(chartViewId) + " Series");
                cartesian.draw(true);
                Log.d("VisualizeFragment", "Data updated for Chart ID: " + chartViewId);
            } else {
                Log.d("VisualizeFragment", "No data to display for Chart ID: " + chartViewId);
            }
        } else {
            Log.d("VisualizeFragment", "Cartesian object not found for Chart ID: " + chartViewId);
        }
    }
    VisualizeFragmentListener mListner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof VisualizeFragmentListener) {
            mListner = (VisualizeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement VisualizeFragmentListener");
        }
    }

    public interface VisualizeFragmentListener {
        void back();
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
}
