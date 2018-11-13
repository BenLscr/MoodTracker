package com.lescour.ben.moodtracker.Controller;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lescour.ben.moodtracker.R;

/**
 * Created by benja on 01/11/2018.
 */
public class Historic extends MainActivity{

   /** @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic);

        LinearLayout mHistoricLayout = (LinearLayout) findViewById(R.id.historicLayout);
        int customWidth = mHistoricLayout.getWidth() / 5;

        TextView mHier = (TextView) findViewById(R.id.hier);
        mHier.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mHier.getLayoutParams().width = customWidth * (clrHier +1);

        TextView mAvHier = (TextView) findViewById(R.id.avHier);
        mAvHier.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mAvHier.setWidth(customWidth);

        TextView mThreeDaysAgo = (TextView) findViewById(R.id.threeDaysAgo);
        mThreeDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mThreeDaysAgo.setWidth(customWidth);

        TextView mFourDaysAgo = (TextView) findViewById(R.id.fourDaysAgo);
        mFourDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mFourDaysAgo.setWidth(customWidth);

        TextView mFiveDaysAgo = (TextView) findViewById(R.id.fiveDaysAgo);
        mFiveDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mFiveDaysAgo.setWidth(customWidth);

        TextView mSixDaysAgo = (TextView) findViewById(R.id.sixDaysAgo);
        mSixDaysAgo.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mSixDaysAgo.setWidth(customWidth);

        TextView mOneWeekAgo = (TextView) findViewById(R.id.oneWeekAgo);
        mOneWeekAgo.setBackgroundColor(getResources().getColor(lst_colors[clrHier]));
        mOneWeekAgo.setWidth(customWidth);
    }*/

}
