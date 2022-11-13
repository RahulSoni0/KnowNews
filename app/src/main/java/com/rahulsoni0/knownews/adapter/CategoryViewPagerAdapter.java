package com.rahulsoni0.knownews.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rahulsoni0.knownews.ui.fragment.EntertainmentFragment;
import com.rahulsoni0.knownews.ui.fragment.HeadlinesFragment;
import com.rahulsoni0.knownews.ui.fragment.PoliticsFragment;
import com.rahulsoni0.knownews.ui.fragment.ScienceFragment;
import com.rahulsoni0.knownews.ui.fragment.SportsFragment;
import com.rahulsoni0.knownews.ui.fragment.TechnologyFragment;

public class CategoryViewPagerAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public CategoryViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HeadlinesFragment headlines = new HeadlinesFragment();
                return headlines;
            case 1:
                SportsFragment sports = new SportsFragment();
                return sports;
            case 2:
                TechnologyFragment tech = new TechnologyFragment();
                return tech;
            case 3:
                ScienceFragment science = new ScienceFragment();
                return science;
            case 4:
                PoliticsFragment politics = new PoliticsFragment();
                return politics;
            case 5:
                EntertainmentFragment entertainment = new EntertainmentFragment();
                return entertainment;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
