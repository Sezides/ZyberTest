package com.example.zybertest;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zybertest.Models.NewsArticle;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        webView = findViewById(R.id.detail_webview);

        NewsArticle article = (NewsArticle) getIntent().getSerializableExtra("article");

        if (article != null && article.getUrl() != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Тёмная тема (Android 10+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webSettings.setForceDark(WebSettings.FORCE_DARK_ON);
            }

            webView.setWebViewClient(new WebViewClient() {
                // Блокировка рекламы (Android 21+)
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    if (isAdUrl(url)) {
                        return new WebResourceResponse("text/plain", "utf-8", null);
                    }
                    return super.shouldInterceptRequest(view, request);
                }

                // Блокировка рекламы (до Android 21)
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    if (isAdUrl(url)) {
                        return new WebResourceResponse("text/plain", "utf-8", null);
                    }
                    return super.shouldInterceptRequest(view, url);
                }

                // Инъекция CSS для тёмной темы (работает на всех версиях Android)
                @Override
                public void onPageFinished(WebView view, String url) {
                    String darkModeCss = "javascript:(function() {" +
                            "var style = document.createElement('style');" +
                            "style.innerHTML = '* { background-color: #121212 !important; color: #e0e0e0 !important; }';" +
                            "document.head.appendChild(style);" +
                            "})();";
                    view.evaluateJavascript(darkModeCss, null);
                    super.onPageFinished(view, url);
                }

                // Простейший фильтр рекламы по URL
                private boolean isAdUrl(String url) {
                    return url.contains("doubleclick.net")
                            || url.contains("ads.")
                            || url.contains("/ads")
                            || url.contains("googlesyndication.com")
                            || url.contains("adservice.google.com")
                            || url.contains("adnxs.com")
                            || url.contains("adsafeprotected.com")
                            || url.contains("pagead/")
                            || url.contains("banner")
                            || url.matches(".*\\bads?\\b.*");
                }
            });

            webView.loadUrl(article.getUrl());
        }
    }
}
