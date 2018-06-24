package com.example.mg.newsapp;

import com.example.mg.newsapp.model.HeadLinesModel;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static int REQUEST_TIMEOUT = 60;
    private static RetrofitInstance instance;
    private IGetData api;

    public interface IloadingData {

        void onLoadingComplete(HeadLinesModel body);

        void onLoading(Disposable disposable);

        void onError(Throwable e);
    }

    public static RetrofitInstance getInstance() {
        if (instance == null) {
            instance = new RetrofitInstance();
        }
        return instance;
    }


    public RetrofitInstance() {
        api = new Retrofit.Builder()
                .baseUrl(IGetData.BASE_URL)
                .client(initOkHttp())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IGetData.class);
    }

    public void getNews(IloadingData iloadingData) {
        api.getHeadlines(IGetData.Country, IGetData.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(headLinesModelObservable(iloadingData));

    }

    private SingleObserver<HeadLinesModel> headLinesModelObservable(final IloadingData iloadingData) {
        return new SingleObserver<HeadLinesModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                iloadingData.onLoading(d);
            }

            @Override
            public void onSuccess(HeadLinesModel headLinesModel) {
                iloadingData.onLoadingComplete(headLinesModel);

            }

            @Override
            public void onError(Throwable e) {
                iloadingData.onError(e);
            }

        };
    }

    private static OkHttpClient initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

}
