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
import com.example.lg.markertree.holder.MyItemViewHolder;
import com.example.lg.markertree.result.my_list.MyItem;
import com.example.lg.markertree.ui.read.ReadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-03-06.
 */
public class MyItemAdapter  extends RecyclerSwipeAdapter<MyItemViewHolder> {
    List<MyItem> items = new ArrayList<>();
    int totalCount;
    Context mContext;

    public MyItemAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void add(MyItem item){
        items.add(item);
        notifyDataSetChanged();
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



    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_my_item, parent, false);
        MyItemViewHolder holder = new MyItemViewHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyItemViewHolder viewHolder, final int position) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });

        viewHolder.surfaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadActivity.class);
                intent.putExtra("bookPos", items.get(position).book_index);
                intent.putExtra("imgUrl","http://52.193.206.21:3000/"+items.get(position).book_thumb); //후에 미리보기 처리
                //intent.putExtra("imgUrl",) 후에 미리보기 처리
                intent.putExtra("bookName", items.get(position).book_name);
                intent.putExtra("bookUrl", items.get(position).book_url);
                mContext.startActivity(intent);
            }
        });

        //삭제버튼
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "삭제 완료 :" + items.get(position).book_index, Toast.LENGTH_SHORT).show();
            }
        });

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
}
