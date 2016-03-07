package com.example.lg.markertree.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.example.lg.markertree.R;
import com.example.lg.markertree.listener.OnItemClickListener;
import com.example.lg.markertree.result.hot_list.HotItem;

import org.w3c.dom.Text;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;

/**
 * Created by LG on 2016-03-01.
 */
public class HotItemViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnailImage;
    TextView txtBook_name,txtBook_url,textcnt,txtFavorite;
    Context mContext;
    ImageView thumbImage;
    public CardView hotBookMark;


    public HotItemViewHolder(View itemView,Context mContext) {
        super(itemView);
        this.mContext = mContext;
        thumbImage=(ImageView)itemView.findViewById(R.id.thumbImage);
        hotBookMark = (CardView)itemView.findViewById(R.id.hotBookMark);
        txtBook_name = (TextView)itemView.findViewById(R.id.textName);
        txtBook_url = (TextView)itemView.findViewById(R.id.textUrl);
        textcnt = (TextView)itemView.findViewById(R.id.Textcnt);
        thumbnailImage = (ImageView)itemView.findViewById(R.id.thumbImage);
        txtFavorite = (TextView)itemView.findViewById(R.id.txtFavorite);
    }


    public void setData(HotItem item){
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
                .into(thumbImage);

        txtFavorite.setText(item.favorite);
        txtBook_name.setText(book_name);
        txtBook_url.setText(item.book_url);
        textcnt.setText(item.cnt);
    }



}
