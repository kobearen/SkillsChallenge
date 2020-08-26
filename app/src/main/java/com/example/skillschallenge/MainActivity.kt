package com.example.skillschallenge


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiButton.setOnClickListener {
            resultApiText.text = ""
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create() //

            val retrofit = Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()


            val client = retrofit.create(Client::class.java)
            client.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("suyaa", it.toString())
                }, {
                    Log.d("suyaa", it.toString())
                })
        }
        }

        button.setOnClickListener {

        }

        readButton.setOnClickListener {
//            var dataString = sp.getString("DataString", null)
            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                .setTitle("タイトル")
//                .setMessage(dataString)
//                .setPositiveButton("OK", { dialog, which ->
//                })
                .show()
        }

        shareButton.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "件名けんめいケンメイ")
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            // URIに対する読み取り権限を付与する
            val SNS_SHARE: Intent = Intent()
            val builder = ShareCompat.IntentBuilder.from(this)
            val intent = builder.createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (intent.resolveActivity(packageManager) != null) {
                ActivityCompat.startActivityForResult(this, sendIntent, 0, null)
            }

        }
        // webViewの練習
//        webView.loadUrl("hhttps://www.google.com/")
//        loadWebpage()
        // SharedPreferencesの練習
//            sp.edit().putString("DataString", "sample")
//            startActivity(share)
    }
}
