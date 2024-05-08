package com.example.assignment12;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment12.fragments.AddLogFragment;
import com.example.assignment12.fragments.ExerciseFragment;
import com.example.assignment12.fragments.MainFragment;
import com.example.assignment12.fragments.QualitySleepFragment;
import com.example.assignment12.fragments.SleepFragment;
import com.example.assignment12.fragments.TimePickerFragment;

import com.example.assignment12.fragments.VisualizeExerciseHoursFragment;
import com.example.assignment12.fragments.VisualizeFragment;
import com.example.assignment12.fragments.VisualizeSleepHoursFragment;
import com.example.assignment12.fragments.VisualizeSleepQualityFragment;
import com.example.assignment12.models.DateTime;
import com.example.assignment12.models.ExerciseHour;
import com.example.assignment12.models.SleepHour;
import com.example.assignment12.models.SleepQuality;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListner,
AddLogFragment.AddLogFragmentListner, SleepFragment.SleepListener, QualitySleepFragment.QualitySleepListener,
ExerciseFragment.ExerciseListener,TimePickerFragment.DateTimeListener,
VisualizeSleepHoursFragment.VisualizeSleepHoursListener,
VisualizeExerciseHoursFragment.VisualizeExerciseHoursFragmentListener,
VisualizeSleepQualityFragment.VisualizeSleepQualityFragmentListener,
VisualizeFragment.VisualizeFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MainFragment())
                .commit();
    }


    @Override
    public void gotoAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddLogFragment(), "add-log-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new VisualizeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void GraphSleephr() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new VisualizeSleepHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void GraphExercisehr() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new VisualizeExerciseHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void GraphWeight() {

    }

    @Override
    public void GraphSleepQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new VisualizeSleepQualityFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void selectSleep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SleepFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectQualitySleep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new QualitySleepFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void selectExercise() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new ExerciseFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void selectDateTime() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new TimePickerFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        Log.d("AddLogFragment", "Cancel called");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submit() {
        Log.d("AddLogFragment", "Submit called");
        getSupportFragmentManager().popBackStack();
    }



    @Override
    public void onSleepQualitySelected(SleepQuality quality) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepQuality = quality;

        }
        getSupportFragmentManager().popBackStack();


    }

    @Override
    public void onSleepSelected(SleepHour sleepHour) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepHour = sleepHour;

        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onExerciseSelected(ExerciseHour hour) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedExerciseHour = hour;

        }
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onDateTimeSelected(DateTime dateTime) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if (fragment != null) {
            fragment.selectedDateTime = dateTime;
            getSupportFragmentManager().popBackStack();
        } else {
            throw new IllegalStateException("AddLogFragment not found");
        }
    }






    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }
}