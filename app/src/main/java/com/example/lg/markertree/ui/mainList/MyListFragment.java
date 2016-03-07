package com.example.lg.markertree.ui.mainList;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lg.markertree.R;

/**
 * Created by LG on 2016-02-24.
 */
public class MyListFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    public static MyListFragment newInstance(){
        MyListFragment fragment = new MyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.mylist_fragment,container,false);

        tabLayout = (TabLayout)v.findViewById(R.id.tablayout);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.getItem(tab.getPosition()).onResume();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

}
