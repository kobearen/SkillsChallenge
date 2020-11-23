package com.example.myapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()//───①
            .build()
        //全件削除
        Realm.deleteRealm(config) // ───②
        Realm.setDefaultConfiguration(config)
    }
}



