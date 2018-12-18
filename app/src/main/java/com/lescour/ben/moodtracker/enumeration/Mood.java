package com.lescour.ben.moodtracker.enumeration;

import com.lescour.ben.moodtracker.R;

/**
 * Created by benja on 16/11/2018.
 */
public enum Mood {
    SAD(R.color.faded_red, R.drawable.smiley_sad, R.raw.song_sad),
    DISAPPOINTED(R.color.warm_grey, R.drawable.smiley_disappointed, R.raw.song_disappointed),
    NORMAL(R.color.cornflower_blue_65, R.drawable.smiley_normal, R.raw.song_normal),
    HAPPY(R.color.light_sage, R.drawable.smiley_happy, R.raw.song_happy),
    SUPER_HAPPY(R.color.banana_yellow, R.drawable.smiley_super_happy, R.raw.song_super_happy);

    private final int color;
    private final int smiley;
    private final int song;

    Mood(int color, int smiley, int song) {
        this.color = color;
        this.smiley = smiley;
        this.song = song;
    }

    public int getColor() {
        return color;
    }

    public int getSmiley() {
        return smiley;
    }

    public int getSong() {
        return song;
    }
}
