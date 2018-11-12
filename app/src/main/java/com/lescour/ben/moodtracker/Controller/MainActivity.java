package com.lescour.ben.moodtracker.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
            R.mipmap.smiley_sad,
            R.mipmap.smiley_disappointed,
            R.mipmap.smiley_normal,
            R.mipmap.smiley_happy,
            R.mipmap.smiley_super_happy
    };
    int [] lst_colors = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
    };
    private String currentDay;
    private final String PREF_KEY_COLOR = "PREF_KEY_COLOR";
    public int clrHier;
    private float initialY;  //Position where you down your finger on the screen.
    private Dialog addComment;
    private EditText commentInput;

    /**
     * Start the application with happy mood, his background color appropriate, historic button and
     * a button to add comment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**if (getColor() != getResources().getColor(R.color.colorAccent)) {
            clrHier = getColor();
        }*/

        // The current date
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        format.setCalendar(calendar);
        currentDay = format.format(calendar.getTime());
        System.out.println(currentDay);

        mMood = new Mood();

        // Start with the happy mood.
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_images[mMood.getLstPosition()]);
        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[mMood.getLstPosition()]));
        /**saveColor(clrPosition);*/

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

    public void addYourComment() {
        addComment = new Dialog(MainActivity.this);
        addComment.setContentView(R.layout.ic_add_comment);
        commentInput = (EditText) addComment.findViewById(R.id.comment_input);
        oldComment();
        Button mAdd = (Button) addComment.findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO penser à sauvegarder le commentaire
                String comment = commentInput.getText().toString();
                mMood.setComment(comment);
                addComment.cancel();
            }
        });
        addComment.show();
    }

    public void oldComment() {
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
                        /**saveColor(clrPosition);*/
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
                        /**saveColor(clrPosition);*/
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mMood.setLstPosition(0);
                        mMood.setLstPosition(mMood.getLstPosition());
                    }
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /**private void saveColor(int clrPosition) {
        mSharedPreferences = getSharedPreferences("backgroundColor", MODE_PRIVATE);
        mSharedPreferences.edit().putInt(PREF_KEY_COLOR, clrPosition).apply();
    }

    private int getColor() {
        mSharedPreferences = getSharedPreferences("backgroundColor", MODE_PRIVATE);
        clrHier = mSharedPreferences.getInt(PREF_KEY_COLOR, getResources().getColor(R.color.colorAccent));
        return clrHier;
    }*/

}
