package com.elearning.client.view.mahasiswa;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.elearning.client.view.dosen.enroll.joined.JoinedFragment;
import com.elearning.client.view.dosen.enroll.requested.RequestedFragment;
import com.elearning.client.view.mahasiswa.kelas.tergabung.TergabungKelasFragment;
import com.elearning.client.view.mahasiswa.kelas.tertunda.TertundaKelasFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] childFragments;

    private int numOfTabs;

    // tab titles
    private String[] tabTitles = new String[]{"TELAH TERGABUNG", "MENUNGGU DISETUJUI"};


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
                return new TergabungKelasFragment();
            case 1:
                return new TertundaKelasFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
