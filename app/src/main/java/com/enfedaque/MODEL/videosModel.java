package com.enfedaque.MODEL;

import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.CONTRACT.videosContract;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class videosModel implements videosContract.Model{


    @Override
    public PeliculasAPI getVideo() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);

        return peliculasAPI;
    }
}
