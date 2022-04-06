package com.enfedaque.API;

import com.enfedaque.domain.respuestaPeliculas;
import com.enfedaque.domain.respuestaUpcoming;
import com.enfedaque.domain.respuestaVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PeliculasAPI {

    @GET("3/movie/popular?api_key=1f3c6e9d137be7318fde0daa6e07d976")
    Call<respuestaPeliculas> findAllPopular();

    @GET("3/movie/top_rated?api_key=1f3c6e9d137be7318fde0daa6e07d976")
    Call<respuestaPeliculas> findAllTopRated();

    @GET("3/movie/popular?api_key=1f3c6e9d137be7318fde0daa6e07d976&page=3")
    Call<respuestaPeliculas> findOthers();

    @GET("3/movie/{movie_id}/videos?api_key=1f3c6e9d137be7318fde0daa6e07d976&language=en-US")
    Call<respuestaVideos> findVideos(@Path("movie_id") String movie_id);

    @GET("3/movie/upcoming?api_key=1f3c6e9d137be7318fde0daa6e07d976")
    Call<respuestaUpcoming> findUpcoming();
}
