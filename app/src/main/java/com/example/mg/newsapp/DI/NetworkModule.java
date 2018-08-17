package com.example.mg.newsapp.DI;

import android.content.Context;

import com.example.mg.newsapp.data.IGetData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @IAppScope
    public Cache provideCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024);
    }

    @Provides
    @IAppScope
    public File provideCacheFile(@IAppContext Context context) {
        return new File(context.getCacheDir(), "okHttp_cache");

    }

    @Provides
    @IAppScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @IAppScope
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return interceptor;
    }

    @Provides
    @IAppScope
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("http://newsapi.org")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @IAppScope
    public IGetData provideRetrofitClient(Retrofit retrofit) {
        return retrofit
                .create(IGetData.class);
    }
}
