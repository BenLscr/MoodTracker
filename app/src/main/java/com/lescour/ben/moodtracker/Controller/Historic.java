package com.lescour.ben.moodtracker.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lescour.ben.moodtracker.Model.Mood;
import com.lescour.ben.moodtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.lang.System.out;

/**
 * Created by benja on 01/11/2018.
 */
public class Historic extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private Calendar calendar;
    private SimpleDateFormat format;
    private Mood mMood;
    private String currentDay;
    private int [] lst_colors = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
    };

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.historic);

       LinearLayout mHistoricLayout = (LinearLayout) findViewById(R.id.historicLayout);
       int customWidth = mHistoricLayout.getWidth() / 5;

       // The current date
       calendar = new GregorianCalendar();
       format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
       format.setCalendar(calendar);
       currentDay = format.format(calendar.getTime());
       out.println(currentDay); // TODO Supprimer cette ligne plus tard

       // YESTERDAY
       TextView mYesterday = (TextView) findViewById(R.id.yesterday);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mYesterday.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mYesterday.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);

       // TWO DAYS AGO
       TextView mTwoDaysAgo = (TextView) findViewById(R.id.twoDaysAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mTwoDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mTwoDaysAgo.setWidth(customWidth);

       // THREE DAYS AGO
       TextView mThreeDaysAgo = (TextView) findViewById(R.id.threeDaysAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mThreeDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mThreeDaysAgo.setWidth(customWidth);

       // FOUR DAYS AGO
       TextView mFourDaysAgo = (TextView) findViewById(R.id.fourDaysAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mFourDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mFourDaysAgo.setWidth(customWidth);

       // FIVE DAYS AGO
       TextView mFiveDaysAgo = (TextView) findViewById(R.id.fiveDaysAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mFiveDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mFiveDaysAgo.setWidth(customWidth);

       // SIX DAYS AGO
       TextView mSixDaysAgo = (TextView) findViewById(R.id.sixDaysAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mSixDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mSixDaysAgo.setWidth(customWidth);

       // A WEEK AGO
       TextView mOneWeekAgo = (TextView) findViewById(R.id.oneWeekAgo);
       decreaseTheDay();
       getADeserializeMood(currentDay);
       mOneWeekAgo.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
       //mOneWeekAgo.setWidth(customWidth);*/
    }

    private String decreaseTheDay() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        currentDay = format.format(calendar.getTime());
        out.println(currentDay); // TODO Supprimer cette ligne plus tard
        return currentDay;
    }

    private void getADeserializeMood(String currentDay) {
        mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        String aMood = mSharedPreferences.getString(currentDay, "{'lstPosition':3}");
        Gson gson = new Gson();
        mMood = gson.fromJson(aMood, Mood.class);
    }

}
