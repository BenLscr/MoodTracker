package com.lescour.ben.moodtracker;

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

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private int [] lst_images = {
            R.mipmap.smiley_sad,
            R.mipmap.smiley_disappointed,
            R.mipmap.smiley_normal,
            R.mipmap.smiley_happy,
            R.mipmap.smiley_super_happy
    };
    private int imgPosition = 3;
    int [] lst_colors = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
    };
    private int clrPosition = 3;
    private final String PREF_KEY_COLOR = "PREF_KEY_COLOR";
    public int clrHier;
    private float initialY;  //Position where you down your finger on the screen.
    private Dialog addComment;
    private EditText commentInput;
    private String comment;

    /**
     * Start the application with happy mood, his background color appropriate, historic button and
     * a button to add comment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getColor() != getResources().getColor(R.color.colorAccent)) {
            clrHier = getColor();
        }
        // Start with the happy mood.
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_images[imgPosition]);
        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));
        saveColor(clrPosition);

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
        commentInput = (EditText) findViewById(R.id.comment_input);
        Button mAdd = (Button) addComment.findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO penser à sauvegarder le commentaire
                //comment = commentInput.getText().toString();
                addComment.cancel();
            }
        });
        addComment.show();
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
                    try {
                        //Increase the position in the background color list / image mood list and display it.
                        clrPosition++;
                        imgPosition++;
                        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));
                        imgSwipe.setImageResource(lst_images[imgPosition]);
                        saveColor(clrPosition);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        clrPosition = 4;
                        imgPosition = 4;
                    }
                }
                if (initialY > finalY) {
                    try {
                        //Decrease the position in the background color list / image mood list and display it.
                        clrPosition--;
                        imgPosition--;
                        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));
                        imgSwipe.setImageResource(lst_images[imgPosition]);
                        saveColor(clrPosition);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        clrPosition = 0;
                        imgPosition = 0;
                    }
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void saveColor(int clrPosition) {
        // TODO essayer de foutre SharedPreferences en valeur (tout en haut)
        mSharedPreferences = getSharedPreferences("backgroundColor", MODE_PRIVATE);
        mSharedPreferences.edit().putInt(PREF_KEY_COLOR, clrPosition).apply();
    }

    private int getColor() {
        // TODO essayer de foutre SharedPreferences en valeur (tout en haut)
        mSharedPreferences = getSharedPreferences("backgroundColor", MODE_PRIVATE);
        clrHier = mSharedPreferences.getInt(PREF_KEY_COLOR, getResources().getColor(R.color.colorAccent));
        return clrHier;
    }

}
