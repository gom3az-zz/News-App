package com.example.mg.newsapp.screens.NewsActivity.DI;

import com.example.mg.newsapp.DI.IAppComponent;
import com.example.mg.newsapp.screens.NewsActivity.NewsActivity;

import dagger.Component;

@IActivityScope
@Component(modules = ActivityModule.class, dependencies = IAppComponent.class)
public interface IActivityComponent {

    void inject(NewsActivity activity);

}
