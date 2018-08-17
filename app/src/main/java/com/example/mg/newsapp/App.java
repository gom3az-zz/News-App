package com.example.mg.newsapp;

import android.app.Activity;
import android.app.Application;

import com.example.mg.newsapp.DI.AppModule;
import com.example.mg.newsapp.DI.DaggerIAppComponent;
import com.example.mg.newsapp.DI.IAppComponent;
import com.example.mg.newsapp.DI.NetworkModule;

public class App extends Application {
    private IAppComponent appComponent;

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerIAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        appComponent.inject(this);
    }

    public IAppComponent getAppComponent() {
        return appComponent;
    }
}
