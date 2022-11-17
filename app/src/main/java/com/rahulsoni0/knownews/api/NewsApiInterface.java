package com.rahulsoni0.knownews.api;

import com.rahulsoni0.knownews.model.NewsContentModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {

    //headlines with country = in

    @GET("/v2/top-headlines")
    Call<NewsContentModel> getBreakingNews(
            @Query("page") int page ,
            @Query("country") String country,
            @Query("apiKey") String apiKey
            );

    @GET("/v2/everything")
    Call<NewsContentModel> getGeneralNews(
            @Query("page") int page ,
            @Query("q") String q ,
            @Query("language") String lang,
            @Query("sortBy") String popularity,
            @Query("apiKey") String apiKey
    );


}
