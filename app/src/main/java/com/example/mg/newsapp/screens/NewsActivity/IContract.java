package com.example.mg.newsapp.screens.NewsActivity;

import com.example.mg.newsapp.model.HeadLinesModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface IContract {
    interface IView {

        void showLoading();

        void hideLoading();

        void showError();

        void initWeatherData(List<HeadLinesModel.Articles> articles);


    }

    interface IActions {

        void onArticleClick(String articleUrl);

        interface IData {
            void onLoadingError();

            void onLoadingSuccess(HeadLinesModel body);

            void onLoading(Disposable disposable);

            void onDestroy();
        }
    }
}
