package com.example.mg.newsapp.screens.NewsActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mg.newsapp.App;
import com.example.mg.newsapp.R;
import com.example.mg.newsapp.data.model.HeadLinesModel;
import com.example.mg.newsapp.screens.NewsActivity.DI.ActivityModule;
import com.example.mg.newsapp.screens.NewsActivity.DI.DaggerIActivityComponent;

import java.util.List;

import javax.inject.Inject;

public class NewsActivity extends AppCompatActivity implements IContract.IView,
        NewsListAdapter.IItemClickListener {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    @Inject
    Presenter mPresenter;

    ProgressBar progressBar;
    RecyclerView weatherRecyclerView;
    @Inject
    NewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        weatherRecyclerView = findViewById(R.id.newsList);
        progressBar = findViewById(R.id.progress_horizontal);

        DaggerIActivityComponent
                .builder()
                .iAppComponent(App.get(this).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, weatherRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        weatherRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
    }

    @Override
    public void initWeatherData(List<HeadLinesModel.Articles> articles) {
        mAdapter.setData(articles);
        weatherRecyclerView.setHasFixedSize(true);
        weatherRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, "Error Loading Data!" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(String articleUrl) {
        mPresenter.onArticleClick(articleUrl);

    }
}
