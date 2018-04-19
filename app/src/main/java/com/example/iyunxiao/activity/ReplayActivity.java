package com.example.iyunxiao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iyunxiao.training.R;


public class ReplayActivity extends AppCompatActivity {
    private WebView webView;
    private TextView loadingText;
    private ProgressBar loadingBar;
    private String webViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replayer_layout);
        webViewUrl = "your url";
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(lp);
        RelativeLayout rlContainer = findViewById(R.id.webRl);
        rlContainer.addView(webView);
        showToWebView();
    }

    @Override
    protected void onStart() {
        Log.e("progress","webviewUrl:"+webViewUrl);
        webView.loadUrl(webViewUrl);
        super.onStart();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void showToWebView() {
        loadingText = findViewById(R.id.replayer_progress_text);
        loadingBar = findViewById(R.id.replayer_progress_bar);
        loadingBar.setIndeterminate(true);
        loadingText.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("process","onPageFinished");
                displayProgressBar();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // TODO : 错误提醒？
                Log.e("process","onReceivedError");
                displayProgressBar();
            }
            @Override
            public void onReceivedHttpError(WebView var1, WebResourceRequest var2, WebResourceResponse var3) {
                super.onReceivedHttpError(var1,var2,var3);
                // TODO : 错误提醒？
                Log.e("process","onReceivedHttpError");
                displayProgressBar();
            }

        });
        webView.setWebChromeClient(new MyWebChromeClient());
        WebSettings webSetting = webView.getSettings();
        //noinspection deprecation
        webSetting.setJavaScriptEnabled(true);
    }

    private void displayProgressBar() {
        Log.e("process","displayProgressBar");
        loadingText.setVisibility(View.GONE);
        loadingBar.setVisibility(View.GONE);
    }

    public void onCloseClick(View v) {
        finish();
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.e("progress",newProgress+"");
            if (newProgress == 100) {
                displayProgressBar();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            ((ViewGroup) webView.getParent()).removeView(webView);
            //noinspection deprecation
            webView.freeMemory();
            webView.clearCache(false);
            //noinspection deprecation
            webView.clearView();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
