package com.example.lg.markertree.ui.mainList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.ImageDecoration;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.result.hot_list.HotItem;
import com.example.lg.markertree.result.hot_list.HotListResult;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by LG on 2016-02-27.
 */
public class Tab1HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    HotItemAdapter mAdapter;
    boolean isLastItem = false; //마지막 item인지 아닌지 판별

    public static Tab1HotFragment newInstance(){
        Tab1HotFragment fragment = new Tab1HotFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mAdapter!=null)
            searchHotList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.main_tab_item_list,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //staggeredGrid
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ImageDecoration(getActivity()));
        mAdapter = new HotItemAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        searchHotList(); //list 불러오기


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItem && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();

                /*LinearLayManager 일 때*/
                //int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                 /*LinearLayManager 일 때*/
                /*if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }*/

                int[] firstVisibleItem = null;
                int pastVisibleItems =0 ;
                firstVisibleItem =((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPositions(firstVisibleItem);
                if(firstVisibleItem != null && firstVisibleItem.length >0){
                    pastVisibleItems = firstVisibleItem[0];
                }

                if(totalItemCount > 0 && (pastVisibleItems + visibleItemCount >= totalItemCount -1)){
                    isLastItem =true;
                }else{
                    isLastItem = false;
                }


            }
        });

        return v;
    }


    /*
    * 리사이클러뷰가 끝에 도달하면 새로운 item들을 가져온다
    */
    public void getMoreItem(){
        int startIndex = mAdapter.getStartIndex();
        if(startIndex != -1){
            MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
            Call<HotListResult> call = myService.readHotList(startIndex, startIndex + 9);

            call.enqueue(new Callback<HotListResult>() {
                @Override
                public void onResponse(Response<HotListResult> response, Retrofit retrofit) {
                    for(HotItem items : response.body().item){
                        mAdapter.add(items);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    //첫화면 띄울때 HotList 10개 띄움
    /* input : 첫 rownum , 마지막 rownum
    *  output: rownum에 따른 list들의 정보
    * */
    private void searchHotList(){
        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
        Call<HotListResult> call = myService.readHotList(1,10);
        call.enqueue(new Callback<HotListResult>() {
            @Override
            public void onResponse(Response<HotListResult> response, Retrofit retrofit) {
                mAdapter.clear();
                mAdapter.setTotalCount(response.body().count);
                for(HotItem hotItem : response.body().item){
                    mAdapter.add(hotItem);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onRefresh() {
        searchHotList();
        refreshLayout.setRefreshing(false);
    }
}
