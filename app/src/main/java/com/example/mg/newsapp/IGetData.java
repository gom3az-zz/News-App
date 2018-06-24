package com.example.mg.newsapp;

import com.example.mg.newsapp.model.HeadLinesModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetData {
    String KEY = "afd77811dc984cfda7f5c0fae4e87b70";
    String BASE_URL = "https://newsapi.org";
    String Country = "us";

    @GET("/v2/top-headlines")
    Single<HeadLinesModel> getHeadlines(@Query("country") String country,
                                        @Query("apiKey") String key);
}
