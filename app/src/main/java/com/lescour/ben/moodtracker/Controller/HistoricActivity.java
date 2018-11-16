package com.lescour.ben.moodtracker.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lescour.ben.moodtracker.Model.Mood;
import com.lescour.ben.moodtracker.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.lescour.ben.moodtracker.Enum.Day.FIVEDAYSAGO;
import static com.lescour.ben.moodtracker.Enum.Day.FOURDAYSAGO;
import static com.lescour.ben.moodtracker.Enum.Day.ONEWEEKAGO;
import static com.lescour.ben.moodtracker.Enum.Day.SIXDAYSAGO;
import static com.lescour.ben.moodtracker.Enum.Day.THREEDAYSAGO;
import static com.lescour.ben.moodtracker.Enum.Day.TWODAYSAGO;
import static com.lescour.ben.moodtracker.Enum.Day.YESTERDAY;
import static com.lescour.ben.moodtracker.Enum.Mood.DISAPPOINTED;
import static com.lescour.ben.moodtracker.Enum.Mood.HAPPY;
import static com.lescour.ben.moodtracker.Enum.Mood.NORMAL;
import static com.lescour.ben.moodtracker.Enum.Mood.SAD;
import static com.lescour.ben.moodtracker.Enum.Mood.SUPER_HAPPY;

/**
 * Created by benja on 01/11/2018.
 */
public class HistoricActivity extends AppCompatActivity {
    private Calendar calendar;
    private SimpleDateFormat format;
    private Mood mMood;
    private String currentDay;
    private int customWidth;
    private int i = 0;
    private List<com.lescour.ben.moodtracker.Enum.Mood> lst_mood = Arrays.asList(SAD,
            DISAPPOINTED, NORMAL, HAPPY, SUPER_HAPPY);
    private List<com.lescour.ben.moodtracker.Enum.Day> lst_day = Arrays.asList(YESTERDAY,
            TWODAYSAGO, THREEDAYSAGO, FOURDAYSAGO, FIVEDAYSAGO, SIXDAYSAGO, ONEWEEKAGO);

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_historic);

       // The current date
       calendar = new GregorianCalendar();
       format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
       format.setCalendar(calendar);
       currentDay = format.format(calendar.getTime());


       LinearLayout mHistoricLayout = (LinearLayout) findViewById(R.id.historicLayout);

       DisplayMetrics displaymetrics = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
       customWidth = displaymetrics.widthPixels / 5;

       do {
           displayLine();
           i++;
       } while (i != 7);
    }

    private void displayLine() {
        decreaseTheDay();
        getADeserializeMood(currentDay);
        TextView tDay = (TextView) findViewById(lst_day.get(i).getText());
        ImageButton bDay = (ImageButton) findViewById(lst_day.get(i).getButton());
        bDay.setVisibility(View.GONE);
        tDay.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bDay.setVisibility(View.VISIBLE);
            bDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fDay = (FrameLayout) findViewById(lst_day.get(i).getFrame());
        fDay.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private String decreaseTheDay() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        currentDay = format.format(calendar.getTime());
        return currentDay;
    }

    private void getADeserializeMood(String currentDay) {
        SharedPreferences mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        String aMood = mSharedPreferences.getString(currentDay, "{'lstPosition':3}");
        Gson gson = new Gson();
        mMood = gson.fromJson(aMood, Mood.class);
    }

}
