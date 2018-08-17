package com.example.mg.newsapp.DI;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.mg.newsapp.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @IAppContext
    @IAppScope
    @Provides
    public Context providesContext() {
        return application;
    }

    @IAppScope
    @Provides
    public RequestManager providesGlide() {
        return Glide.with(application);
    }
}
