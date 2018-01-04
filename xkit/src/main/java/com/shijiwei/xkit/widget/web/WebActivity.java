package com.shijiwei.xkit.widget.web;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import com.shijiwei.xkit.R;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.utility.log.LogUtil;
import com.shijiwei.xkit.widget.component.title.TitleBar;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by shijiwei on 2016/11/26.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener, BrowserPicker.CallBack, TitleBar.TitleBarCallBack {

    private static final String TAG = "WebActivity";

    private final String SPECIFICATION_URL = "";
    private final String KEY_PARAMS_URL = "url";
    private final String KEY_PARAMS_TITLE = "title";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ImageView mPrePageButton;
    private ImageView mNextPageButton;
    private ImageView mMoreButton;
    private ImageView mRefreshButton;
    private TitleBar mTitleBar;

    private String mURL = "http://www.gogen.com.cn/";
    private String mTitle = "高锦科技";
    private int mStep = 0;

    private BrowserPicker mBrowserPicker;
    private List<ResolveInfo> mDataSet;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_PARAMS_URL)) {
            String url = intent.getStringExtra(KEY_PARAMS_URL);
            if (url != null && url.length() > 0)
                mURL = url;
        }
        if (intent.hasExtra(KEY_PARAMS_TITLE)) {
            String title = intent.getStringExtra(KEY_PARAMS_TITLE);
            if (title != null && title.length() > 0)
                mTitle = title;
        }

        if (!mURL.contains("//")) {
            mURL = "http://" + mURL;
        }
    }

    @Override
    public void initialView() {

        mTitleBar = (TitleBar) findViewById(R.id.titel_bar);
        mProgressBar = (ProgressBar) findViewById(R.id.web_progress_bar);
        mWebView = (WebView) findViewById(R.id.web_view);
        mPrePageButton = (ImageView) findViewById(R.id.iv_pre_page_button);
        mNextPageButton = (ImageView) findViewById(R.id.iv_next_page_button);
        mMoreButton = (ImageView) findViewById(R.id.iv_more_button);
        mRefreshButton = (ImageView) findViewById(R.id.iv_refresh_button);
        mBrowserPicker = new BrowserPicker(this);
    }

    @Override
    public void initialEvn() {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //以下两条设置可以使页面适应手机屏幕的分辨率，完整的显示在屏幕上
        //设置是否使用WebView推荐使用的窗口
        webSettings.setUseWideViewPort(true);
        //设置WebView加载页面的模式
        webSettings.setLoadWithOverviewMode(true);
        //支持屏幕缩放、隐藏缩放指示控件
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

        mPrePageButton.setOnClickListener(this);
        mNextPageButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
        mRefreshButton.setOnClickListener(this);
        mBrowserPicker.setCallBack(this);

        mTitleBar.setTitleBarCallBack(this);
        mTitleBar.setTitel(mTitle);
        loadUrl(mURL);
    }

    @Override
    public void onBackKeyPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
            isCanGoBackOrForward();
        } else {
            finish();
        }
    }

    @Override
    public void onNetworkStateChanged(int type, boolean isAvailable) {

    }

    /**
     * Changing the state of the UI depending on whether the web page can move forward or backward
     */
    private void isCanGoBackOrForward() {

        if (mWebView.canGoBack()) {
            mPrePageButton.setEnabled(true);
        } else {
            mPrePageButton.setEnabled(false);
        }

        if (mWebView.canGoForward()) {
            mNextPageButton.setEnabled(true);
        } else {
            mNextPageButton.setEnabled(false);
        }
    }

    /**
     * Load the specified url
     *
     * @param url
     */
    private void loadUrl(String url) {
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.iv_pre_page_button) {
            mWebView.goBack();
        } else if (v.getId() == R.id.iv_next_page_button) {
            mWebView.goForward();
        } else if (v.getId() == R.id.iv_more_button) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mURL));
            mDataSet = getPackageManager().queryIntentActivities(intent, 0);
            if (mDataSet != null) {
                mBrowserPicker.show();
                mBrowserPicker.notifyDataChanged(mDataSet);
            } else {

            }
        } else if (v.getId() == R.id.iv_refresh_button) {
            loadUrl(mWebView.getUrl());
        }

    }

    @Override
    public void callBack(int position) {

        mBrowserPicker.dismiss();

        ResolveInfo resolveInfo = mDataSet.get(position);
        String activityPackageName = resolveInfo.activityInfo.packageName;
        String className = resolveInfo.activityInfo.name;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mURL));
        ComponentName componentName = new ComponentName(activityPackageName, className);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    @Override
    public void onLeftButonClick(View v) {
        finish();
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            LogUtil.d(TAG, " shouldOverrideUrlLoading " + url);
            if (url.equalsIgnoreCase(SPECIFICATION_URL)) {
                //TODO
            } else {
                loadUrl(url);
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isCanGoBackOrForward();
            LogUtil.d(TAG, "onPageFinished");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //mWebView.loadData(error.getErrorCode() + "", "text/html", "utf-8");
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100)
                mProgressBar.setVisibility(View.GONE);
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * 通过反射隐藏缩放页面的部件
     *
     * @param view
     */
    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
