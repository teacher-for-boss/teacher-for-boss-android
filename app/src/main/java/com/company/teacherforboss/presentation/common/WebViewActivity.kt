package com.company.teacherforboss.presentation.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityWebViewBinding
import com.company.teacherforboss.util.base.BindingActivity

class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        loadWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initLayout() {
        binding.wvWebView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
            webChromeClient = WebChromeClient()

            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
            }
        }
    }

    private fun loadWebView() {
        intent.getStringExtra(WEB_VIEW_LINK)?.let { binding.wvWebView.loadUrl(it) }
    }

    companion object {
        const val WEB_VIEW_LINK = "WebViewLink"
    }
}
