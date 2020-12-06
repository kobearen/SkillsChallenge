package com.example.skillschallenge

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

// ネットワークの接続状態を入れるLiveData
class NetworkStateLiveData(context: Context): LiveData<Boolean>() {
    // ネットワーク情報を取得するために、ネットワーク情報を管理しているクラスConnectivityManagerを使用
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // LiveDataが1つでもObserveされている場合に呼ばれる
    override fun onActive() {
        super.onActive()
        // NetworkRequestに調べたいネットワークの種類をaddTransportType()メソッドでセット
        // ネットワークの接続状態の変化を受け取るコールバックを登録
        // 第1引数：NetworkRequest、第2引数：NetworkCallback
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    // LiveDataが1つもObserveされていない場合に呼ばれる
    override fun onInactive() {
        super.onInactive()
        // コールバック解除
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    // ネットワークの接続状態の変化を受け取るコールバック
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // ネットワークが使用可能になったときに呼ばれる
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            // LiveDataにtrueを渡す
            postValue(true)
        }

        // ネットワークの接続が切れたときに呼ばれる
        override fun onLost(network: Network) {
            super.onLost(network)
            // LiveDataにfalseを渡す
            postValue(false)
        }
    }
}