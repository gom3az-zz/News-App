package com.example.mg.newsapp.data;


import com.example.mg.newsapp.data.model.HeadLinesModel;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataInteractor {

    private final IGetData mRetrofitClient;

    public DataInteractor(IGetData retrofitClient) {
        mRetrofitClient = retrofitClient;
    }

    public Single<HeadLinesModel> loadOnlineWeather() {
        return mRetrofitClient.getHeadlines(IGetData.Country, IGetData.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}