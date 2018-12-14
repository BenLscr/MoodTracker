package com.lescour.ben.moodtracker.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lescour.ben.moodtracker.model.Mood;
import com.lescour.ben.moodtracker.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.lescour.ben.moodtracker.enumeration.Day.FIVEDAYSAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.FOURDAYSAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.ONEWEEKAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.SIXDAYSAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.THREEDAYSAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.TWODAYSAGO;
import static com.lescour.ben.moodtracker.enumeration.Day.YESTERDAY;
import static com.lescour.ben.moodtracker.enumeration.Mood.DISAPPOINTED;
import static com.lescour.ben.moodtracker.enumeration.Mood.HAPPY;
import static com.lescour.ben.moodtracker.enumeration.Mood.NORMAL;
import static com.lescour.ben.moodtracker.enumeration.Mood.SAD;
import static com.lescour.ben.moodtracker.enumeration.Mood.SUPER_HAPPY;

/**
 * Created by benja on 01/11/2018.
 */
public class HistoricActivity extends AppCompatActivity {
    private Calendar calendar;
    private SimpleDateFormat format;
    private Mood mMood;
    private String currentDay;
    private int customWidth;
    private List<com.lescour.ben.moodtracker.enumeration.Mood> lst_mood = Arrays.asList(SAD,
            DISAPPOINTED, NORMAL, HAPPY, SUPER_HAPPY);
    private List<com.lescour.ben.moodtracker.enumeration.Day> lst_day = Arrays.asList(YESTERDAY,
            TWODAYSAGO, THREEDAYSAGO, FOURDAYSAGO, FIVEDAYSAGO, SIXDAYSAGO, ONEWEEKAGO);

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_historic);
       theCalendar();
       displayMetrics();
       displayLine();
    }

    /**
     * Build a calendar and display it like year-month-day.
     */
    private void theCalendar() {
        calendar = new GregorianCalendar();
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        format.setCalendar(calendar);
        currentDay = format.format(calendar.getTime());
           }

    /**
     * Recover the width of the screen and divide it by number of mood in my lst_mood.
     */
    private void displayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        customWidth = displaymetrics.widthPixels / lst_mood.size();
    }

    /**
     * Display every line for the last seven days
     */
    private void displayLine() {
        for (int i = 0; i < lst_day.size(); i++) {
            decreaseTheDay();
            getADeserializeMood(currentDay);
            FrameLayout fDay = findViewById(lst_day.get(i).getFrame());
            fDay.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
            fDay.getLayoutParams().width = customWidth * (mMood.getLstPosition() + 1);
            ImageButton bDay = findViewById(lst_day.get(i).getButton());
            bDay.setVisibility(View.GONE);
            if (mMood.getComment() != null) {
                final String comment = mMood.getComment();
                bDay.setVisibility(View.VISIBLE);
                bDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HistoricActivity.this, comment,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /**
     * at every use of this method, the date is decrease by 1.
     */
    private void decreaseTheDay() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        currentDay = format.format(calendar.getTime());
    }

    /**
     * Recover the save Mood who corresponds at the current day in the calendar.
     * If no save is found, set the basic Mood.
     * Deserialize the save Mood and and display his data to the class Mood.
     * @param currentDay the current day in the calendar. He's use like a key.
     */
    private void getADeserializeMood(String currentDay) {
        SharedPreferences mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        String aMood = mSharedPreferences.getString(currentDay, "{'lstPosition':3}");
        Gson gson = new Gson();
        mMood = gson.fromJson(aMood, Mood.class);
    }

}
