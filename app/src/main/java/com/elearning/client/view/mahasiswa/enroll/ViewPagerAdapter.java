package com.elearning.client.view.mahasiswa.enroll;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.elearning.client.view.mahasiswa.enroll.joined.JoinedFragment;
import com.elearning.client.view.mahasiswa.enroll.requested.RequestedFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] childFragments;

    private int numOfTabs;

    // tab titles
    private String[] tabTitles = new String[]{"TELAH TERGABUNG", "MEMINTA PERSETUJUAN"};


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
                return new JoinedFragment();
            case 1:
                return new RequestedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
