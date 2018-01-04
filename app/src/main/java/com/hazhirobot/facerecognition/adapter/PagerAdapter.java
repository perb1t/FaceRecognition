package com.hazhirobot.facerecognition.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by shijiwei on 2017/11/6.
 *
 * @VERSION 1.0
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mPagerSet;

    public PagerAdapter(FragmentManager fm, List<Fragment> pagerSet) {
        super(fm);
        this.mPagerSet = pagerSet;
    }

    @Override
    public Fragment getItem(int position) {
        return mPagerSet.get(position);
    }

    @Override
    public int getCount() {
        return mPagerSet.size();
    }
}
