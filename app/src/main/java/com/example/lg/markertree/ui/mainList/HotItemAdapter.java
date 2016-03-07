package com.example.lg.markertree.ui.mainList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.lg.markertree.R;

import com.example.lg.markertree.listener.OnItemClickListener;
import com.example.lg.markertree.holder.HotItemViewHolder;
import com.example.lg.markertree.result.hot_list.HotItem;
import com.example.lg.markertree.ui.read.ReadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-02-18.
 */
public class HotItemAdapter extends RecyclerSwipeAdapter<HotItemViewHolder> implements OnItemClickListener {
    List<HotItem> items = new ArrayList<HotItem>();
    int totalCount;
    Context mContext;
    int curPage = 0;

    public HotItemAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void add(HotItem item){
        items.add(item);
        notifyDataSetChanged();
    }
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
        if (items.size() < totalCount) {
            return items.size() + 1;
        }
        return -1;
    }

    public void clear(){
        totalCount = 0;
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public HotItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_hot_item, parent, false);
        HotItemViewHolder holder = new HotItemViewHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotItemViewHolder viewHolder, final int position) {
    /*
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });*/

        viewHolder.hotBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadActivity.class);

                intent.putExtra("bookPos", items.get(position).book_idx);
                intent.putExtra("imgUrl","http://52.193.206.21:3000/"+items.get(position).book_thumb); //후에 미리보기 처리
                intent.putExtra("bookName", items.get(position).book_name);
                intent.putExtra("bookUrl", items.get(position).book_url);
                mContext.startActivity(intent);
            }
        });


        /*//저장버튼
        viewHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"등록 완료 :"+items.get(position).book_idx,Toast.LENGTH_SHORT).show();
            }
        });
        */
        viewHolder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.hotSwipeLayout;
    }

    OnItemClickListener itemClickListener;
    @Override
    public void onItemClick(View view, int positon) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view,positon);
        }
    }
}
