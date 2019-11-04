package com.example.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.parse_Tour.tour_fragment;
import com.example.fragment.parse_course.course_fragment;
import com.example.fragment.parse_hotel.hotel_fragment;

import java.util.ArrayList;

public class MypagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mData;
    public MypagerAdapter(FragmentManager fm) {

        super(fm);
        mData = new ArrayList<>();
        mData.add(new course_fragment());
        mData.add(new tour_fragment());
        mData.add(new hotel_fragment());


    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
            String name[] = {"코스추천","관광지","호텔"};
            return name[position];
    }
}