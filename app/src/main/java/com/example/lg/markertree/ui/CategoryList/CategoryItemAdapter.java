package com.example.lg.markertree.ui.CategoryList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.CategoryItemViewHolder;
import com.example.lg.markertree.result.category_list.CategoryItem;
import com.example.lg.markertree.ui.read.ReadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-03-06.
 */
public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemViewHolder> {
    List<CategoryItem> items = new ArrayList<>();
    int totalCount;
    Context mContext;
    public CategoryItemAdapter(Context mContext){
        this.mContext = mContext;
    }
    public void setTotalCount(int totalCount){this.totalCount = totalCount;}

    public int getStartIndex() {
        if (items.size() < totalCount) {
            return items.size() + 1;
        }
        return -1;
    }


    public void add(CategoryItem item){
        items.add(item);
    }

    public void clear(){
        totalCount = 0;
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_hot_item, parent, false);
        CategoryItemViewHolder holder = new CategoryItemViewHolder(view,mContext);

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, final int position) {

        holder.setData(items.get(position));
        holder.hotBookMark.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
