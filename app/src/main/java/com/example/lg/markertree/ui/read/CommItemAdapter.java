package com.example.lg.markertree.ui.read;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lg.markertree.R;
import com.example.lg.markertree.holder.CommItemViewHolder;
import com.example.lg.markertree.result.read_comm.CommItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-03-04.
 */
public class CommItemAdapter extends RecyclerView.Adapter<CommItemViewHolder> {
    List<CommItem> items = new ArrayList<CommItem>();
    Context mContext;

    public CommItemAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void add(CommItem item){
        items.add(item);
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public CommItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_comm_item, parent, false);
        CommItemViewHolder holder = new CommItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CommItemViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
