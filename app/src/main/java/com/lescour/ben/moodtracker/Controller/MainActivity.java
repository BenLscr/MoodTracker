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

import com.google.gson.Gson;
import com.lescour.ben.moodtracker.Model.Mood;
import com.lescour.ben.moodtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private Mood mMood;
    private int [] lst_images = {
            R.drawable.smiley_sad,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,
            R.drawable.smiley_happy,
            R.drawable.smiley_super_happy,
    };
    private int [] lst_colors = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
    };
    private String currentDay;
    private float initialY;  //Position where you down your finger on the screen.
    private Dialog addComment;
    private EditText commentInput;
    private String jsonMood;
    private String lastMood;

    /**
     * Start the application with happy mood, his background color appropriate, historic button and
     * a button to add comment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The current date
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        format.setCalendar(calendar);
        currentDay = format.format(calendar.getTime());

        getMyLastMood();
        deserializeMyLastMood(lastMood);

        // Start with the happy mood.
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_images[mMood.getLstPosition()]);
        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));

        ImageButton icNoteAdd = (ImageButton) findViewById(R.id.icNoteAdd);
        ImageButton icHistory = (ImageButton) findViewById(R.id.icHistory);

        icNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYourComment();
            }
        });

        icHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historic = new Intent(MainActivity.this, Historic.class);
                startActivity(historic);
            }
        });
    }

    /**
     * The dialog box to add a comment
     */
    public void addYourComment() {
        addComment = new Dialog(MainActivity.this);
        addComment.setContentView(R.layout.ic_add_comment);
        commentInput = (EditText) addComment.findViewById(R.id.comment_input);
        currentComment();
        Button mCancel = (Button) addComment.findViewById(R.id.cancel);
        mCancel.setTextColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment.cancel();
            }
        });
        Button mValidate = (Button) addComment.findViewById(R.id.validate);
        mValidate.setTextColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
        mValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentInput.getText().toString();
                mMood.setComment(comment);
                addComment.cancel();
            }
        });
        commentInput.getBackground().mutate().setColorFilter(getResources()
                .getColor(lst_colors[mMood.getLstPosition()]), PorterDuff.Mode.SRC_ATOP);
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
     * @param motionEvent
     * @return
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
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
                ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
                if (initialY < finalY) {
                    try { //Increase the position in the background color list / image mood list and display it.
                        mMood.setLstPosition(mMood.getLstPosition() + 1);
                        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
                        imgSwipe.setImageResource(lst_images[mMood.getLstPosition()]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mMood.setLstPosition(4);
                        mMood.setLstPosition(mMood.getLstPosition());
                    }
                }
                if (initialY > finalY) {
                    try { //Decrease the position in the background color list / image mood list and display it.
                        mMood.setLstPosition(mMood.getLstPosition() - 1);
                        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
                        imgSwipe.setImageResource(lst_images[mMood.getLstPosition()]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mMood.setLstPosition(0);
                        mMood.setLstPosition(mMood.getLstPosition());
                    }
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /**
     * Save the current Mood (mood/comment) when user leave the application.
     */
    @Override
    protected void onStop() {
        super.onStop();
        serializeMood();
        saveMyMood(jsonMood);
    }

    /**
     * Serialization of data of the class Mood.
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

    /**
     * Give the mood to display. If it's a new day or a the same day but you need to update .
     * @return Serialize data.
     */
    private String getMyLastMood() {
        mSharedPreferences = getSharedPreferences("myMood", MODE_PRIVATE);
        lastMood = mSharedPreferences.getString(currentDay, "{'lstPosition':3}");
        return lastMood;
    }

    /**
     * Deserialization of lastMood and display data to the class Mood.
     */
    private void deserializeMyLastMood(String lastMood) {
        Gson gson = new Gson();
        mMood = gson.fromJson(lastMood, Mood.class);
    }

}