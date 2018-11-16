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
    private List<com.lescour.ben.moodtracker.Enum.Mood> lst_mood = Arrays.asList(SAD, DISAPPOINTED, NORMAL, HAPPY, SUPER_HAPPY);

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

       yesterday();
       twoDaysAgo();
       threeDaysAgo();
       fourDaysAgo();
       fiveDaysAgo();
       sixDaysAgo();
       oneWeekAgo();
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

    private void yesterday() {
        TextView mYesterday = (TextView) findViewById(R.id.yesterdayText);
        ImageButton bYesterday = (ImageButton) findViewById(R.id.yesterdayButton);
        bYesterday.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mYesterday.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bYesterday.setVisibility(View.VISIBLE);
            bYesterday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fYesterday = (FrameLayout) findViewById(R.id.yesterdayFrame);
        fYesterday.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void twoDaysAgo() {
        TextView mTwoDaysAgo = (TextView) findViewById(R.id.twoDaysAgoText);
        ImageButton bTwoDaysAgo = (ImageButton) findViewById(R.id.twoDaysAgoButton);
        bTwoDaysAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mTwoDaysAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bTwoDaysAgo.setVisibility(View.VISIBLE);
            bTwoDaysAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fTwoDaysAgo = (FrameLayout) findViewById(R.id.twoDaysAgoFrame);
        fTwoDaysAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void threeDaysAgo() {
        TextView mThreeDaysAgo = (TextView) findViewById(R.id.threeDaysAgoText);
        ImageButton bThreeDaysAgo = (ImageButton) findViewById(R.id.threeDaysAgoButton);
        bThreeDaysAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mThreeDaysAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bThreeDaysAgo.setVisibility(View.VISIBLE);
            bThreeDaysAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fThreeDaysAgo = (FrameLayout) findViewById(R.id.threeDaysAgoFrame);
        fThreeDaysAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void fourDaysAgo() {
        TextView mFourDaysAgo = (TextView) findViewById(R.id.fourDaysAgoText);
        ImageButton bFourDaysAgo = (ImageButton) findViewById(R.id.fourDaysAgoButton);
        bFourDaysAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mFourDaysAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bFourDaysAgo.setVisibility(View.VISIBLE);
            bFourDaysAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fFourDaysAgo = (FrameLayout) findViewById(R.id.fourDaysAgoFrame);
        fFourDaysAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void fiveDaysAgo() {
        TextView mFiveDaysAgo = (TextView) findViewById(R.id.fiveDaysAgoText);
        ImageButton bFiveDaysAgo = (ImageButton) findViewById(R.id.fiveDaysAgoButton);
        bFiveDaysAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mFiveDaysAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bFiveDaysAgo.setVisibility(View.VISIBLE);
            bFiveDaysAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fFiveDaysAgo = (FrameLayout) findViewById(R.id.fiveDaysAgoFrame);
        fFiveDaysAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void sixDaysAgo() {
        TextView mSixDaysAgo = (TextView) findViewById(R.id.sixDaysAgoText);
        ImageButton bSixDaysAgo = (ImageButton) findViewById(R.id.sixDaysAgoButton);
        bSixDaysAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mSixDaysAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bSixDaysAgo.setVisibility(View.VISIBLE);
            bSixDaysAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fSixDaysAgo = (FrameLayout) findViewById(R.id.sixDaysAgoFrame);
        fSixDaysAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }

    private void oneWeekAgo() {
        TextView mOneWeekAgo = (TextView) findViewById(R.id.oneWeekAgoText);
        ImageButton bOneWeekAgo = (ImageButton) findViewById(R.id.oneWeekAgoButton);
        bOneWeekAgo.setVisibility(View.GONE);
        decreaseTheDay();
        getADeserializeMood(currentDay);
        mOneWeekAgo.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        if (mMood.getComment() != null ) {
            final String comment = mMood.getComment();
            bOneWeekAgo.setVisibility(View.VISIBLE);
            bOneWeekAgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoricActivity.this,comment,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        FrameLayout fOneWeekAgo = (FrameLayout) findViewById(R.id.oneWeekAgoFrame);
        fOneWeekAgo.getLayoutParams().width = customWidth * (mMood.getLstPosition() +1);
    }
}
