package com.example.mg.newsapp.screens.NewsActivity;

import android.content.Intent;

import com.example.mg.newsapp.data.DataInteractor;
import com.example.mg.newsapp.screens.webViewACtivity.WebView;

import io.reactivex.disposables.CompositeDisposable;

public class Presenter implements IContract.IActions {

    private final NewsActivity mView;
    private final DataInteractor mDataInteractor;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public Presenter(NewsActivity newsActivity, DataInteractor dataInteractor) {
        mView = newsActivity;
        mDataInteractor = dataInteractor;
    }

    @Override
    public void onStart() {
        mDisposable.add(mDataInteractor.loadOnlineWeather()
                .doOnSubscribe(x -> mView.showLoading())
                .doFinally(mView::hideLoading)
                .subscribe(
                        (headLinesModel) -> mView.initWeatherData(headLinesModel.getArticles()),
                        mView::showError
                )
        );
    }

    @Override
    public void onStop() {
        mDisposable.clear();
    }

    @Override
    public void onArticleClick(String articleUrl) {
        Intent intent = new Intent(mView, WebView.class);
        intent.putExtra("url", articleUrl);
        mView.startActivity(intent);
    }

}
