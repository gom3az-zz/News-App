package com.example.mg.newsapp.screens.NewsActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mg.newsapp.NewsListAdapter;
import com.example.mg.newsapp.R;
import com.example.mg.newsapp.model.HeadLinesModel;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements IContract.IView,
        NewsListAdapter.IItemClickListener {
    private Presenter mPresenter;
    private NewsListAdapter mAdapter;
    ProgressBar progressBar;
    RecyclerView weatherRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        weatherRecyclerView = findViewById(R.id.newsList);
        progressBar = findViewById(R.id.progress_horizontal);
        mPresenter = new Presenter(this);
    }

    @Override
    protected void onDestroy() {
        mAdapter = null;
        mPresenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public void initWeatherData(List<HeadLinesModel.Articles> articles) {
        mAdapter = new NewsListAdapter(this, articles);
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
    public void showError() {
        Toast.makeText(this, "Error Loading Data!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(String articleUrl) {
        mPresenter.onArticleClick(articleUrl);

    }
}
