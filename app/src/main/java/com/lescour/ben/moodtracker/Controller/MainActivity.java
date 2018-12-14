package com.lescour.ben.moodtracker.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private Mood mMood;
    private String currentDay;
    private List<com.lescour.ben.moodtracker.Enum.Mood> lst_mood = Arrays.asList(SAD, DISAPPOINTED, NORMAL, HAPPY, SUPER_HAPPY);
    private float initialY;  //Position where you down your finger on the screen.
    private Dialog addComment;
    private EditText commentInput;
    private String jsonMood;
    private String lastMood;

    /**
     * Start the application with happy mood, his appropriate background color , historic button and
     * a button to add comment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theCalendar();
        getMyLastMood();
        deserializeMyLastMood(lastMood);
        displayCurrentMood();
        ImageButton icNoteAdd = findViewById(R.id.icNoteAdd);
        ImageButton icHistory = findViewById(R.id.icHistory);

        icNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYourComment();
            }
        });
        icHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historic = new Intent(MainActivity.this, HistoricActivity.class);
                startActivity(historic);
            }
        });
    }

    /**
     * Build a calendar and display it like year-month-day.
     */
    private void theCalendar() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        format.setCalendar(calendar);
        currentDay = format.format(calendar.getTime());
    }

    /**
     * Recover the mood to display from external memory. If it's a new day, display the basic mood.
     * If you reopen the application in the same day, set the current mood..
     */
    private void getMyLastMood() {
        mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        lastMood = mSharedPreferences.getString(currentDay, "{'lstPosition':3}");
    }

    /**
     * Deserialization of lastMood and display his data to the class Mood.
     */
    private void deserializeMyLastMood(String lastMood) {
        Gson gson = new Gson();
        mMood = gson.fromJson(lastMood, Mood.class);
    }

    /**
     * Display the current image and color.
     */
    private void displayCurrentMood() {
        FrameLayout frameLayout = findViewById(R.id.frame_layout);
        ImageView imgSwipe = findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_mood.get(mMood.getLstPosition()).getSmiley());
        frameLayout.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
    }

    /**
     * The dialog box to add a comment.
     */
    public void addYourComment() {
        addComment = new Dialog(MainActivity.this);
        addComment.setContentView(R.layout.ic_add_comment);
        TextView comment = addComment.findViewById(R.id.comment);
        comment.setTextColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        commentInput = addComment.findViewById(R.id.comment_input);
        currentComment();
        Button mCancel = addComment.findViewById(R.id.cancel);
        mCancel.setTextColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment.cancel();
            }
        });
        Button mValidate = addComment.findViewById(R.id.validate);
        mValidate.setTextColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
        mValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentInput.getText().toString();
                mMood.setComment(comment);
                addComment.cancel();
            }
        });
        commentInput.getBackground().mutate().setColorFilter(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()), PorterDuff.Mode.SRC_ATOP);
        addComment.show();
    }

    /**
     * Display the current comment if you return in the dialog box.
     */
    public void currentComment() {
        String comment = mMood.getComment();
        if (comment != null) {
            commentInput.setText(comment);
        }
    }

    /**
     * Detect the swipe to change the mood and his background color.
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                //Memories the position where you down your finger on the screen.
                initialY = motionEvent.getY();
                break;
            case (MotionEvent.ACTION_UP):
                float finalY = motionEvent.getY();
                FrameLayout frameLayout = findViewById(R.id.frame_layout);
                ImageView imgSwipe = findViewById(R.id.imgSwipe);
                if (initialY < finalY) {
                    try { //Increase the position in the background color list / image mood list and display it.
                        mMood.setLstPosition(mMood.getLstPosition() + 1);
                        frameLayout.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
                        imgSwipe.setImageResource(lst_mood.get(mMood.getLstPosition()).getSmiley());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mMood.setLstPosition(lst_mood.size()-1);
                    }
                }
                if (initialY > finalY) {
                    try { //Decrease the position in the background color list / image mood list and display it.
                        mMood.setLstPosition(mMood.getLstPosition() - 1);
                        frameLayout.setBackgroundColor(getResources().getColor(lst_mood.get(mMood.getLstPosition()).getColor()));
                        imgSwipe.setImageResource(lst_mood.get(mMood.getLstPosition()).getSmiley());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mMood.setLstPosition(0);
                    }
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /**
     * Save the current Mood (mood/comment) serialized when user leave the application.
     */
    @Override
    protected void onStop() {
        super.onStop();
        serializeMood();
        saveMyMood(jsonMood);
    }

    /**
     * Serialization of current data of the class Mood.
     */
    private void serializeMood() {
        Gson gson = new Gson();
        jsonMood = gson.toJson(mMood);
    }

    /**
     * Save data in external memory.
     * @param jsonMood The serialize data of the class Mood.
     */
    private void saveMyMood(String jsonMood) {
        mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        mSharedPreferences.edit().putString(currentDay, jsonMood).apply();
    }

}