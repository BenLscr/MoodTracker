package com.lescour.ben.moodtracker.Model;

/**
 * Created by benja on 12/11/2018.
 */
public class Mood {
    private String comment;
    private int lstPosition = 3;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLstPosition() {
        return lstPosition;
    }

    public void setLstPosition(int lstPosition) {
        this.lstPosition = lstPosition;
    }
}
