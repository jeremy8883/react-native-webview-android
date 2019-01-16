package com.burnweb.rnwebview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewWithSwipeRefresh extends SwipeRefreshLayout {
    private RNWebView webView;

    public WebViewWithSwipeRefresh(Context context, final RNWebView webView) {
        super(context);
        this.webView = webView;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

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

    private int mTouchSlop;
    private float mPrevX;
    private boolean mHasGoneHorizontal = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                mHasGoneHorizontal = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mHasGoneHorizontal) return false;

                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    mHasGoneHorizontal = true;
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
