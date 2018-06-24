package com.example.mg.newsapp.screens.NewsActivity;

import android.content.Intent;

import com.example.mg.newsapp.model.HeadLinesModel;
import com.example.mg.newsapp.screens.webViewACtivity.WebView;

import io.reactivex.disposables.Disposable;

public class Presenter implements IContract.IActions, IContract.IActions.IData {

    private NewsActivity mView;
    private Disposable disposable;

    Presenter(NewsActivity newsActivity) {
        mView = newsActivity;
        new DataInteractor(this);
    }

    @Override
    public void onLoading(Disposable d) {
        disposable = d;
        mView.showLoading();
    }

    @Override
    public void onLoadingError(Throwable e) {
        mView.showError(e);
        mView.hideLoading();
    }

    @Override
    public void onLoadingSuccess(HeadLinesModel body) {
        mView.initWeatherData(body.getArticles());
        mView.hideLoading();
    }

    @Override
    public void onArticleClick(String articleUrl) {
        Intent intent = new Intent(mView, WebView.class);
        intent.putExtra("url", articleUrl);
        mView.startActivity(intent);
    }


    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}
