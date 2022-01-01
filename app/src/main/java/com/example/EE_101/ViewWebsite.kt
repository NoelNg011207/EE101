package com.example.EE_101

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

import android.webkit.WebViewClient

class ViewWebsite() : AppCompatActivity() {
    lateinit private var webView : WebView
    lateinit private var webUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_website)
        webView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webUrl = intent.getStringExtra("url").toString()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        if(webUrl.contains(".pdf")){
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+webUrl)
        }else{
            webView.loadUrl(webUrl)
        }
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}