package com.burnweb.rnwebview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewWithSwipeRefresh extends SwipeRefreshLayout {
    private RNWebView webView;

    public WebViewWithSwipeRefresh(Context context, final RNWebView webView) {
        super(context);
        this.webView = webView;

        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
        webView.setParentForDispatchId(this);
        webView.setAdditionalWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });
        addView(webView);
    }

    public RNWebView getWebView() {
        return this.webView;
    }
}
