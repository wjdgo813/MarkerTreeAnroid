package com.example.lg.markertree.ui.mainList;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Switch;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm){super(fm);}

    @Override
    public Fragment getItem(int position) {
        Fragment resultFragment = null;
        switch(position){
            case 0:
                resultFragment = Tab1HotFragment.newInstance();
                break;
            case 1:
                resultFragment = Tab2MyFragment.newInstance();
                break;
            case 2:
                resultFragment = Tab3FrdListFragment.newInstance();
                break;

        }
        return resultFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title="";
        switch (position){
            case 0:
                title = "인기 리스트";
                break;
            case 1:
                title ="내 목록";
                break;
            case 2:
                title = "TAB3";
                break;
        }
        return title;
    }
}
