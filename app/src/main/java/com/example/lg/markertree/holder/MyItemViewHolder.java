package com.example.lg.markertree.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.example.lg.markertree.R;
import com.example.lg.markertree.listener.OnItemClickListener;
import com.example.lg.markertree.result.hot_list.HotItem;
import com.example.lg.markertree.result.my_list.MyItem;

/**
 * Created by LG on 2016-03-06.
 */
public class MyItemViewHolder extends RecyclerView.ViewHolder{
    public SwipeLayout swipeLayout;
    Context mContext;
    ImageView thumbnailImage;
    TextView txtBook_name,txtBook_url,txtFavorite;
    public RelativeLayout surfaceLayout;
    public Button deleteBtn;
    public MyItemViewHolder(View itemView,Context mContext) {
        super(itemView);
        this.mContext = mContext;
        surfaceLayout = (RelativeLayout)itemView.findViewById(R.id.surfaceLayout);
        swipeLayout = (SwipeLayout)itemView.findViewById(R.id.hotSwipeLayout);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left,itemView.findViewById(R.id.bottom_wrapper2));
        txtBook_name = (TextView)itemView.findViewById(R.id.textName);
        txtBook_url = (TextView)itemView.findViewById(R.id.textUrl);
        thumbnailImage = (ImageView)itemView.findViewById(R.id.thumbImage);
        deleteBtn = (Button)itemView.findViewById(R.id.deleteBtn);
        txtFavorite = (TextView)itemView.findViewById(R.id.txtFavorite);


    }

    public void setData(MyItem item){
        String book_name="제목 없음";
        if(!TextUtils.isEmpty(item.book_name)){
            book_name = item.book_name;
        }
        String imageUrl="http://52.193.206.21:3000/"+item.book_thumb;
        Glide.with(mContext)
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(thumbnailImage);
        txtFavorite.setText(item.book_favorite);
        txtBook_name.setText(book_name);
        txtBook_url.setText(item.book_url);
    }


}
