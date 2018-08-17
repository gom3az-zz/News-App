package com.example.mg.newsapp.data;

import com.example.mg.newsapp.data.model.HeadLinesModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetData {
    String KEY = "81a452c672f74416bbe4d78d9891ca93";
    String Country = "us";

    @GET("/v2/top-headlines")
    Single<HeadLinesModel> getHeadlines(@Query("country") String country,
                                        @Query("apiKey") String key);
}
