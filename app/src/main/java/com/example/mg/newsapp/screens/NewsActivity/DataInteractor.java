package com.example.mg.newsapp.screens.NewsActivity;


import com.example.mg.newsapp.RetrofitInstance;
import com.example.mg.newsapp.model.HeadLinesModel;
import com.example.mg.newsapp.screens.NewsActivity.IContract.IActions.IData;

import io.reactivex.disposables.Disposable;

class DataInteractor implements RetrofitInstance.IloadingData {

    private IData iData;

    DataInteractor(Presenter presenter) {
        iData = presenter;
        RetrofitInstance.getInstance().getNews(this);
    }

    @Override
    public void onLoadingComplete(HeadLinesModel body) {
        iData.onLoadingSuccess(body);
    }

    @Override
    public void onLoading(Disposable disposable) {
        iData.onLoading(disposable);
    }

    @Override
    public void onError(Throwable e) {
        iData.onLoadingError();
    }


}