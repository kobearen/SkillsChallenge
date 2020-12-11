package com.example.skillschallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

const val NETWORK_ADDRESS ="https://tanukigolf.com/"

class WebViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // WebViewのデバッグを有効にする
        WebView.setWebContentsDebuggingEnabled(true)
        val v: View = inflater.inflate(R.layout.fragment_web_view, container, false)
        val mWebView = v.findViewById<View>(R.id.web_view) as WebView
        //webview内のJavaScriptを有効にする
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl(NETWORK_ADDRESS)
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}