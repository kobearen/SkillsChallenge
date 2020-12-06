package com.example.skillschallenge


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkStateLiveData = NetworkStateLiveData(applicationContext)
        networkStateLiveData.observe(this@MainActivity, Observer { networkState ->
            if (!networkState) {
                // ネットワークから切断した場合
                println("ネットワークエラー")
                offline()
            }
        })
        // webViewの練習
        // webView.loadUrl("hhttps://www.google.com/")
        // loadWebpage()
        val mBtn = findViewById<Button>(R.id.button)
        mBtn.setOnClickListener {
        // SharedPreferencesの練習
        // sp.edit().putString("DataString", "sample")
        // startActivity(share)
        }

        val readButton = findViewById<Button>(R.id.readButton)
        readButton.setOnClickListener {
//            var dataString = sp.getString("DataString", null)
            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                .setTitle("タイトル")
//                .setMessage(dataString)
//                .setPositiveButton("OK", { dialog, which ->
//                })
                .show()
        }

        val shareButton = findViewById<Button>(R.id.shareButton)
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


//            startActivity(shareIntent)
//            val builder = ShareCompat.IntentBuilder.from(this)
//
//            builder.setSubject("件名けんめいケンメイ")
//            builder.setText("今なら、アプリをインストールして、会員登録(無料)すると、お友だち紹介スタンプ"
//                        + "個プレゼント中！" + "\n"
////                    + R.string.share_invitation_bodypresent + "\n"
//                        + "さらに、"
//                        + "までにジョイフル店舗にご来店頂き、来店スタンプをGETすると、招待してくれたお友達も紹介スタンプがGETできます。" + "\n"
//                        + "招待リンク期限:"
//                        + "23:59まで" + "\n"
////                    + R.string.share_invitation_bodylast + "\n"
//                        + "https://joyfullinvite.page.link?inviter_id=[id]&hash=[hash]"
//            )
//
////            {   "title":"お得な友だち紹介スタンプ!",
////                "description":"ジョイフルアプリの招待状♪今ならJSONきれい",
////                "hash_tag":"#テスト",
////                "invitee_expire_at":"2020-05-01 00:00:00",
////                "invitee_point":10
////                }
//
//            builder.setType("text/plain")
//            builder.startChooser()
        }
    }
    //
    fun isOnline(): Boolean {
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    fun loadWebpage() {

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 ->
                // 戻りを受け取って何らか処理する
                // resultCode は必ずゼロになるので、 RESULT_OK で判定しない
//            println("SNS_SHARE")
            println(resultCode)

            else -> super.onActivityResult(requestCode, resultCode, data)
        }
        // 戻るでも入ってくるので注意
        println(data)
    }

    fun offline(){ // オフライン の時はダイアログ
        if (!isOnline()){
            val dialog = SimpleDialogFragment()
            dialog.show(supportFragmentManager, "simple")
        }}

}



// listViewの練習
//        val arrayAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1).apply {
//            add("Android")
//            add("iOS")
//            add("Windows")
//            add("macOS")
//            add("Unix")
//            add("+") // 追加
//        }

//        val listView : ListView = findViewById(R.id.listView)
//        listView.adapter = arrayAdapter
//
//        // 項目をタップしたときの処理
//        listView.setOnItemClickListener {parent, view, position, id ->
//
//            // 項目の TextView を取得
//            val itemTextView : TextView = view.findViewById(android.R.id.text1)
//
//            // 項目のラベルテキストをログに表示
//            Log.i("debug", itemTextView.text.toString())
//
//            // 一番下の項目をタップしたら新しい項目をその項目の上に追加
//            if (position == arrayAdapter.count - 1) {
//                arrayAdapter.insert("New Item " + arrayAdapter.count, arrayAdapter.count - 1)
//                arrayAdapter.notifyDataSetChanged()
//            }
//        }

//        // 項目を長押ししたときの処理
//        listView.setOnItemLongClickListener { parent, view, position, id ->
//
//            // 一番下の項目以外は長押しで削除
//            if (position == arrayAdapter.count - 1) {
//                return@setOnItemLongClickListener false
//            }
//
//            arrayAdapter.remove(arrayAdapter.getItem(position))
//            arrayAdapter.notifyDataSetChanged()
//
//            return@setOnItemLongClickListener true
//        }

//        val sp = PreferenceManager.getDefaultSharedPreferences(null)