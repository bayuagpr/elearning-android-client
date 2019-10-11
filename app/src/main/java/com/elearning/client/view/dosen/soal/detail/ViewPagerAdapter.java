package com.elearning.client.view.dosen.soal.detail;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.elearning.client.view.dosen.hasil.early.EarlyHasilFragment;
import com.elearning.client.view.dosen.hasil.late.LateHasilFragment;
import com.elearning.client.view.dosen.materi.MateriFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] childFragments;

    private int numOfTabs;

    // tab titles
    private String[] tabTitles = new String[]{"TEPAT WAKTU", "TERLAMBAT"};


    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EarlyHasilFragment();
            case 1:
                return new LateHasilFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
