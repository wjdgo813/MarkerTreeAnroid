package com.example.lg.markertree.ui.mainList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lg.markertree.R;

/**
 * Created by LG on 2016-02-27.
 */
public class Tab3FrdListFragment extends Fragment {
    public static Tab3FrdListFragment newInstance(){
        Tab3FrdListFragment fragment = new Tab3FrdListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.main_tab3_frdlist_fragment,container,false);

        return v;
    }
}
