package com.example.iyunxiao.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.*
import com.example.iyunxiao.training.R
import kotlinx.android.synthetic.main.activity_webview.*


class WebviewActivity : AppCompatActivity() {
    val TAG =WebviewActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        var url = "your url"
        //var url = "http://www.baidu.com"
        wb.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e(TAG,"onPageFinished:"+url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e(TAG,"shouldOverrideUrlLoading:"+url)
                view!!.loadUrl(url!!)
                return true
            }
        }
        wb.webChromeClient = object:WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.e(TAG,"newProgress:"+newProgress+"")
            }
        }

        wb.getSettings().setJavaScriptEnabled(true)
        //noinspection deprecation
        wb.loadUrl(url)
    }
}
