package com.example.lg.markertree.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.lg.markertree.result.create_book_mark.CreateResult;
import com.example.lg.markertree.session.Values;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by LG on 2016-03-06.
 */
/*
* 북마크 저장하는 클래스
* input : context,email,좋아요,url,댓글,카테고리
* output : Toast 메시지
* */
public class SignUpMark {
    String email,goodOrBad,url,comment,favorite;
    Context mContext;
    public SignUpMark(Context mContext, String email, String goodOrBad, String url, String comment, String favorite){
        this.mContext = mContext;
        this.email = email;
        this.goodOrBad = goodOrBad;
        this.url = url;
        this.comment = comment;
        this.favorite = favorite;
    }

    public void sendMark() {
        try {
            if(!url.contains(".")){
                Toast.makeText(mContext,"URL을 제대로 입력하세요.",Toast.LENGTH_SHORT).show();
            }else {
                if (!url.contains("http://")) {
                    url = "http://" + url;
                }

                email = URLEncoder.encode(email, "UTF-8");
                goodOrBad = URLEncoder.encode(goodOrBad, "UTF-8"); //좋아요 or 나빠요

                url = URLEncoder.encode(url, "UTF-8");
                comment = URLEncoder.encode(comment, "UTF-8");
                //favorite : 카테고리
                favorite = URLEncoder.encode(favorite, "UTF-8");

                MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
                Call<CreateResult> call = myService.createBookMark(email, favorite, url, comment, goodOrBad);
                call.enqueue(new Callback<CreateResult>() {
                    @Override
                    public void onResponse(Response<CreateResult> response, Retrofit retrofit) {

                        if (response.body().success == 1) {
                            Toast.makeText(mContext, response.body().result.message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, response.body().result.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
