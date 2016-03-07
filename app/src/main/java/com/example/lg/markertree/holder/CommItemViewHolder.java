package com.example.lg.markertree.holder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lg.markertree.R;
import com.example.lg.markertree.result.read_comm.CommItem;

/**
 * Created by LG on 2016-03-04.
 */
public class CommItemViewHolder extends RecyclerView.ViewHolder{
    TextView txtUserName,txtComm,txtDate;
    ImageView imgGoodOrBad;


    public CommItemViewHolder(View itemView) {
        super(itemView);

        txtUserName =(TextView)itemView.findViewById(R.id.txtUserName);
        txtComm = (TextView)itemView.findViewById(R.id.txtComm);
        txtDate= (TextView)itemView.findViewById(R.id.txtDate);
        imgGoodOrBad = (ImageView)itemView.findViewById(R.id.imgGoodOrBad);
    }

    public void setData(CommItem item){
        String comm = item.com_comment;
        txtComm.setText(comm.replace("+"," ")); // 댓글내용
        txtUserName.setText(item.user_name); // 유저 이름
        txtDate.setText(item.register_date); // 등록 날짜

        if(item.com_pros.equals("G")){
            imgGoodOrBad.setImageResource(R.drawable.good);
        }else{
            imgGoodOrBad.setImageResource(R.drawable.bad);
        }
    }
}
