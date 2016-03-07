package com.example.lg.markertree.network;

import com.example.lg.markertree.result.category_list.CategoryResult;
import com.example.lg.markertree.result.create_book_mark.CreateResult;
import com.example.lg.markertree.result.hot_list.HotListResult;
import com.example.lg.markertree.result.login.LoginResult;
import com.example.lg.markertree.result.my_list.MyListResult;
import com.example.lg.markertree.result.read_comm.ReadCommResult;
import com.example.lg.markertree.result.signUp.SignupResult;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by LG on 2016-02-23.
 */
public interface MyService {
    @FormUrlEncoded
    @POST("/sign_up")
    Call<SignupResult> signUp(
            @Field("user_email") String user_email,
            @Field("user_pw") String user_pw,
            @Field("user_name") String user_name,
            @Field("user_favorite") String user_favorite,
            @Field("user_birth") String user_birth,
            @Field("user_gender") String user_gender
            );

    @FormUrlEncoded
    @POST("/login")
    Call<LoginResult> login(
            @Field("user_email") String user_email,
            @Field("user_pw") String user_pw
    );

    @FormUrlEncoded
    @POST("/create")
    Call<CreateResult> createBookMark(
            @Field("user_email") String user_email,
            @Field("book_favorite") String book_favorite,
            @Field("book_url") String book_url,
            @Field("com_comment") String com_comment,
            @Field("com_pros") String com_pros
    );

    @FormUrlEncoded
    @POST("/hot_list")
    Call<HotListResult> readHotList(
            @Field("startIndex") int startIndex,
            @Field("endIndex") int endIndex
    );

    @FormUrlEncoded
    @POST("/read")
    Call<ReadCommResult> readComm(
            @Field("bookIndex") int index
    );

    @FormUrlEncoded
    @POST("/my_list")
    Call<MyListResult> callMyList(
            @Field("book_favorite") String book_favorite,
            @Field("user_email") String user_email,
            @Field("startIndex") int startIndex,
            @Field("endIndex") int endIndex
    );



    @FormUrlEncoded
    @POST("/read_favorite")
    Call<CategoryResult> callCategoryList(
            @Field("book_favorite") String book_favorite,
            @Field("startIndex") int startIndex,
            @Field("endIndex") int endIndex
    );


}
