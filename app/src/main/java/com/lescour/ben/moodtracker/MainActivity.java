package com.lescour.ben.moodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

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
    private float initialY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start with the happy mood.
        ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        imgSwipe.setImageResource(lst_images[imgPosition]);
        imgSwipe.getResources().getColor(clrPosition);
        imgSwipe.setBackgroundColor(lst_colors[clrPosition]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                initialY = motionEvent.getY();
                break;
            case (MotionEvent.ACTION_UP):
                float finalY = motionEvent.getY();
                if (initialY < finalY) {
                    ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
                    imgPosition++;
                    imgSwipe.setImageResource(lst_images[imgPosition]);
                    Toast.makeText(this, "Up to Down swipe performed", Toast.LENGTH_SHORT).show();
                }
                if (initialY > finalY) {
                    ImageView imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
                    imgPosition--;
                    imgSwipe.setImageResource(lst_images[imgPosition]);
                    Toast.makeText(this, "Down to Up swipe performed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }


}
