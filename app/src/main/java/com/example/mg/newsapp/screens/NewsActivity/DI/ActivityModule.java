package com.example.mg.newsapp.screens.NewsActivity.DI;

import com.example.mg.newsapp.data.DataInteractor;
import com.example.mg.newsapp.data.IGetData;
import com.example.mg.newsapp.screens.NewsActivity.NewsActivity;
import com.example.mg.newsapp.screens.NewsActivity.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final NewsActivity activity;

    public ActivityModule(NewsActivity activity) {
        this.activity = activity;
    }

    @IActivityScope
    @Provides
    NewsActivity providesContext() {
        return activity;
    }

    @IActivityScope
    @Provides
    Presenter providesPresenter(NewsActivity activity, DataInteractor interactor) {
        return new Presenter(activity, interactor);
    }

    @IActivityScope
    @Provides
    DataInteractor providesDataInteractor(IGetData retrofitClient) {
        return new DataInteractor(retrofitClient);
    }

}
