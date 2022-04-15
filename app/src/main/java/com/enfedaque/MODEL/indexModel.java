package com.enfedaque.MODEL;

import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.CONTRACT.indexContract;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class indexModel implements indexContract.Model{

    @Override
    public PeliculasAPI getInstance() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);
        return peliculasAPI;
    }
}
