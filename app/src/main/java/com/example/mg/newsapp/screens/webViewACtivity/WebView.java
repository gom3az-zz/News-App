package com.example.mg.newsapp.screens.webViewACtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.mg.newsapp.R;

public class WebView extends AppCompatActivity {
    android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView = findViewById(R.id.webview);
        final ProgressBar pbar = findViewById(R.id.pB1);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(android.webkit.WebView view, int progress) {
                if (progress < 100 && pbar.getVisibility() == ProgressBar.GONE) {
                    pbar.setVisibility(ProgressBar.VISIBLE);
                }
                pbar.setProgress(progress);
                if (progress >= 80) {
                    pbar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onDetachedFromWindow() {
        destroyWebView();
        super.onDetachedFromWindow();
    }

    public void destroyWebView() {

        webView.clearHistory();
        webView.clearCache(true);
        webView.loadUrl("about:blank");
        webView.onPause();
        webView.removeAllViews();
        webView.destroyDrawingCache();
        webView.setWebViewClient(null);
        webView.pauseTimers();
        webView.setTag(null);
        webView.destroy();
        webView = null;
    }
}
