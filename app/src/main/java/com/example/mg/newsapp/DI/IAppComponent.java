package com.example.mg.newsapp.DI;


import com.bumptech.glide.RequestManager;
import com.example.mg.newsapp.App;
import com.example.mg.newsapp.data.IGetData;

import dagger.Component;

@IAppScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface IAppComponent {

    IGetData iGetData();

    RequestManager glide();

    void inject(App app);
}
