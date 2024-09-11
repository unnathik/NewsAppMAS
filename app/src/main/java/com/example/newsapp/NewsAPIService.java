package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPIService {
    @GET("v2/top-headlines")
    Call<AllNews> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
