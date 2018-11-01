package com.lescour.ben.moodtracker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int [] lst_images = {
            R.mipmap.smiley_sad,
            R.mipmap.smiley_disappointed,
            R.mipmap.smiley_normal,
            R.mipmap.smiley_happy,
            R.mipmap.smiley_super_happy
    };
    private int imgPosition = 3;
    private int [] lst_colors = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
    };
    private int clrPosition = 3;
    private float initialY;  //Position where you down your finger on the screen.

    /**
     * Start the application with happy mood, his background color appropriate, historic button and
     * a button to add comment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start with the happy mood.
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_images[imgPosition]);
        frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));

        ImageButton icNoteAdd = (ImageButton) findViewById(R.id.icNoteAdd);
        ImageButton icHistory = (ImageButton) findViewById(R.id.icHistory);

        icNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYourComment();
            }
        });
    }

    public void addYourComment() {
        final Dialog addComment = new Dialog(MainActivity.this);
        //addComment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addComment.setContentView(R.layout.ic_add_comment);

        Button mAdd = (Button) addComment.findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //penser à sauvegarder le commentaire
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
                if (initialY < finalY) {
                    //Increase the position in the background color list and display it.
                    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
                    clrPosition++;
                    frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));
                    //Increase the position in the image mood list and display it.
                    ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
                    imgPosition++;
                    imgSwipe.setImageResource(lst_images[imgPosition]);
                }
                if (initialY > finalY) {
                    //Decrease the position in the background color list and display it.
                    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
                    clrPosition--;
                    frameLayout.setBackgroundColor(getResources().getColor(lst_colors[clrPosition]));
                    //Decrease the position in the image mood list and display it.
                    ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
                    imgPosition--;
                    imgSwipe.setImageResource(lst_images[imgPosition]);
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }


}
