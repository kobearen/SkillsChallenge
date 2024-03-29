package com.example.skillschallenge

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_web_view.*

const val NETWORK_ADDRESS =
    "https://pkg.navitime.co.jp/sevenbank/"  // https://kobearen.hatenablog.com/entry/task"
const val NETWORK_ADDRESS_SECOND = "https://pkg.navitime.co.jp/sevenbank/"
//const val NETWORK_ADDRESS = "http://192.168.128.200:8080/#/"

//const val NETWORK_ADDRESS = "http://192.168.128.233:8081/#/confirm_email"
class WebViewFragment : Fragment() {
    private lateinit var mWebView: WebView

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
        // WebView内のJavaScriptを有効にする
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.addJavascriptInterface(this, "Android")//第１引数には@JavascriptInterfaceを書いてあるクラス名を書く


        (activity as? MainActivity)?.let { activity ->
            mWebView.settings.javaScriptEnabled = true
            mWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url == "https://kobearen.hatenablog.com/entry/task") {
                        //URLが"/https://kobearen.hatenablog.com/entry/task
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 横画面固定
                        return true
                    } else {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 縦画面固定
                    }
                    return true
                }
            }
        }
        mWebView.loadUrl("javascript:window.location.href='" + NETWORK_ADDRESS + "'")
        // web_view.loadUrl("https://tanukigolf.com/")
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_load_js.setOnClickListener {
            loadJS("javascript: isConnectedDevice('braino',true)")
            loadJS("javascript: isAttachedDevice('braino',true)")
        }
        btn_remove_fragment.setOnClickListener {
            removeFragment()
        }
    }

    @JavascriptInterface
    fun startSensorDataTransfer(recordingId: String) {
        // デバイスから受信した計測データを計算し始める
        println("recordingId=$recordingId")
    }

    fun loadJS(javascript: String) {
        // 画面のロードが完了していない場合は画面のロード後にjavascriptを叩く
        web_view.evaluateJavascript(javascript, null)
    }

    fun removeFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.remove(this)
        fragmentTransaction?.commit()
    }
}