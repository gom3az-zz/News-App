package com.example.mg.newsapp.screens.NewsActivity;

import com.example.mg.newsapp.data.model.HeadLinesModel;

import java.util.List;

public interface IContract {
    interface IView {

        void showLoading();

        void hideLoading();

        void showError(Throwable e);

        void initWeatherData(List<HeadLinesModel.Articles> articles);

    }

    interface IActions {

        void onArticleClick(String articleUrl);

        void onStart();

        void onStop();

    }
}
