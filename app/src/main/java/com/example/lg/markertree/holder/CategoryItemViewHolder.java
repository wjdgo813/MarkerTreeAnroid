package com.example.lg.markertree.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lg.markertree.R;
import com.example.lg.markertree.result.category_list.CategoryItem;

/**
 * Created by LG on 2016-03-06.
 */
public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnailImage;
    TextView txtBook_name,txtBook_url,textcnt,txtFavorite;
    public CardView hotBookMark;


    public CategoryItemViewHolder(View itemView) {
        super(itemView);
        hotBookMark = (CardView)itemView.findViewById(R.id.hotBookMark);
        txtBook_name = (TextView)itemView.findViewById(R.id.textName);
        txtBook_url = (TextView)itemView.findViewById(R.id.textUrl);
        textcnt = (TextView)itemView.findViewById(R.id.Textcnt);
        thumbnailImage = (ImageView)itemView.findViewById(R.id.thumbImage);
        txtFavorite = (TextView)itemView.findViewById(R.id.txtFavorite);
    }


    public void setData(CategoryItem item){
        String book_name="제목 없음";
        if(!TextUtils.isEmpty(item.book_name)){
            book_name = item.book_name;
        }
        txtFavorite.setText(item.favorite);
        txtBook_name.setText(book_name);
        txtBook_url.setText(item.book_url);
        textcnt.setText(item.cnt);
    }
}
