package com.example.lg.markertree.ui.CategoryList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.ImageDecoration;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.result.category_list.CategoryItem;
import com.example.lg.markertree.result.category_list.CategoryResult;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by LG on 2016-03-06.
 */
public class CategoryListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    CategoryItemAdapter mAdapter;
    Boolean isLastItem = false;
    String favorite="";
    public static CategoryListFragment newInstance(){
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mAdapter!=null)
            searchCategoryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.main_tab_item_list, container, false);
        Bundle extra = getArguments();
        favorite = extra.getString("favorite");
        Toast.makeText(getActivity(),favorite+" dd",Toast.LENGTH_SHORT).show();


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);


        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new ImageDecoration(getActivity()));
        mAdapter = new CategoryItemAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        searchCategoryList();

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
            Call<CategoryResult> call = myService.callCategoryList(favorite, startIndex, startIndex + 9);

            call.enqueue(new Callback<CategoryResult>() {
                @Override
                public void onResponse(Response<CategoryResult> response, Retrofit retrofit) {
                    for(CategoryItem items : response.body().item){
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
    private void searchCategoryList(){
        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
        Call<CategoryResult> call = myService.callCategoryList(favorite,1,10);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Response<CategoryResult> response, Retrofit retrofit) {
                mAdapter.clear();
                mAdapter.setTotalCount(response.body().count);
                for (CategoryItem categoryItem : response.body().item) {
                    mAdapter.add(categoryItem);
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
        searchCategoryList();
        refreshLayout.setRefreshing(false);
    }
}
