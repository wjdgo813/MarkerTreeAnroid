package com.example.lg.markertree.ui.mainList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.ImageDecoration;
import com.example.lg.markertree.network.MyService;
import com.example.lg.markertree.network.NetworkManager;
import com.example.lg.markertree.result.my_list.MyItem;
import com.example.lg.markertree.result.my_list.MyListResult;
import com.example.lg.markertree.session.Values;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by LG on 2016-02-27.
 */
public class Tab2MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    MyItemAdapter mAdapter;
    Button categoryBtn;
    boolean isLastItem = false;


    public static Tab2MyFragment newInstance(){
        Tab2MyFragment fragment = new Tab2MyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mAdapter!=null){
            callMyList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.main_tab2_my_item_list,container,false);
        categoryBtn = (Button)v.findViewById(R.id.categoryBtn);
        categoryBtn.setText("전체");

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ImageDecoration(getActivity()));
        mAdapter = new MyItemAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        callMyList();

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
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                 /*LinearLayManager 일 때*/
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });



        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingle();
            }
        });
        return v;
    }



    @Override
    public void onRefresh() {
        callMyList();
        refreshLayout.setRefreshing(false);
    }

    /*
    * 리사이클러뷰가 끝에 도달하면 새로운 item들을 가져온다
    */
    public void getMoreItem(){
        int startIndex = mAdapter.getStartIndex();
        if(startIndex != -1){
            MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
            Call<MyListResult> call = myService.callMyList(favorite,Values.setting.getString("email", ""),startIndex, startIndex + 9);

            call.enqueue(new Callback<MyListResult>() {
                @Override
                public void onResponse(Response<MyListResult> response, Retrofit retrofit) {
                    if(response.code()==200) {
                        for (MyItem items : response.body().item) {
                            mAdapter.add(items);
                        }
                    }else{
                        Toast.makeText(getActivity(), response.body().item.get(0).message,Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    // MyList 10개 띄움
    /* input : 사용자 email, 첫 rownum , 마지막 rownum
    *  output: rownum에 따른 list들의 정보
    * */
    private void callMyList(){
        MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
        Call<MyListResult> call = myService.callMyList(favorite,Values.setting.getString("email", ""),1,10);
        call.enqueue(new Callback<MyListResult>() {
            @Override
            public void onResponse(Response<MyListResult> response, Retrofit retrofit) {
                mAdapter.clear();
                mAdapter.setTotalCount(response.body().count);
                for(MyItem myItem : response.body().item){
                    mAdapter.add(myItem);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    //다이얼로그 설정 , 카테고리 설정
    String[] favoriteList = {"전체","운동","예술","게임","독서","음악","공부","기타"};
    String tmpFavorite="";
    String favorite="전체";
    int lastDialSelectPos=0;
    int tmplastDialSelectPos;

    public void showSingle(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("카테고리를 선택해주세요.");

        builder.setSingleChoiceItems(favoriteList, lastDialSelectPos, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tmpFavorite = favoriteList[which];
                tmplastDialSelectPos=which;
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(tmpFavorite)) {
                    favorite = favoriteList[lastDialSelectPos]; //아무것도 클릭 안했을시에 기본값 예술로 지정
                } else {
                    favorite = tmpFavorite; //yes를 눌러야만 값 저장하도록 유도
                    lastDialSelectPos = tmplastDialSelectPos;
                }
                categoryBtn.setText(favorite);
                 //카테고리 설정이 끝나면 서버에 전송
                callMyList();
            }
        });

        builder.create().show();
    }
}
