package com.lescour.ben.moodtracker.enumeration;

import com.lescour.ben.moodtracker.R;

/**
 * Created by benja on 16/11/2018.
 */
public enum Day {
    YESTERDAY(R.id.yesterdayText, R.id.yesterdayButton, R.id.yesterdayFrame),
    TWODAYSAGO(R.id.twoDaysAgoText, R.id.twoDaysAgoButton, R.id.twoDaysAgoFrame),
    THREEDAYSAGO(R.id.threeDaysAgoText, R.id.threeDaysAgoButton, R.id.threeDaysAgoFrame),
    FOURDAYSAGO(R.id.fourDaysAgoText, R.id.fourDaysAgoButton, R.id.fourDaysAgoFrame),
    FIVEDAYSAGO(R.id.fiveDaysAgoText, R.id.fiveDaysAgoButton, R.id.fiveDaysAgoFrame),
    SIXDAYSAGO(R.id.sixDaysAgoText, R.id.sixDaysAgoButton, R.id.sixDaysAgoFrame),
    ONEWEEKAGO(R.id.oneWeekAgoText, R.id.oneWeekAgoButton, R.id.oneWeekAgoFrame);

    private final int text;
    private final int button;
    private final int frame;

    Day(int text, int button, int frame) {
        this.text = text;
        this.button = button;
        this.frame = frame;
    }

    public int getText() {
        return text;
    }

    public int getButton() {
        return button;
    }

    public int getFrame() {
        return frame;
    }
}
