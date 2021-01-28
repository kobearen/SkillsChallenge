package com.example.skillschallenge


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File


const val NETWORK_ERROR = "file:///android_asset/error_0.html"

class MainActivity : AppCompatActivity() {

    private var lastId: String = "0"
    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkStateLiveData = NetworkStateLiveData(applicationContext)
        networkStateLiveData.observe(this@MainActivity, Observer { networkState ->
            if (!networkState) {
                // ネットワークから切断した場合
                println("ネットワークエラー")
            }
        })

        // ファイル書き出しと保存
        btnSaveFile.setOnClickListener {
            print("offline")
            var webView = findViewById<View>(R.id.web_view) as WebView
            webView.loadUrl(NETWORK_ERROR)

            //ボタンを消す
            btnSaveFile.visibility = View.INVISIBLE
        }
    }
}