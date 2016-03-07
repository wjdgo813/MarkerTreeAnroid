package com.example.lg.markertree.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by LG on 2016-02-23.
 */
public class NetworkManager {
    private static final NetworkManager networkManager = new NetworkManager();

    private NetworkManager(){}

    public static NetworkManager getInstance(){
        return networkManager;
    }

    public <T> T getRetrofit(Class<T> aa){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.APP_URL).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(aa);
    }
}
