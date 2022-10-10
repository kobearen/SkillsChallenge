package com.example.skillschallenge


import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File

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
                offline()
            }
        })

        // ファイル書き出しと保存
        btnSaveFile.setOnClickListener {
            // Save file  // get string contents of EditText
            val contents = editFileSave.text.toString()
            if (contents.isNotEmpty()) {
                saveFile("fileName", contents)
                textView.text = getString(R.string.saved)
            } else {
                textView.text = getString(R.string.no_text)
            }
        }

        // ファイル読み取り
        btnReadFile.setOnClickListener {
            val str = readFiles("fileName")
            if (str != null) {
                textView.text = str
            } else {
                textView.text = getString(R.string.no_text)
            }
        }

        // atm webview
        btn_atm.setOnClickListener {
            val webViewFragment = WebViewFragment()
            val secondFragment = SecondFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_container, secondFragment)
            fragmentTransaction.add(R.id.fragment_container, webViewFragment)
            fragmentTransaction.commit()

            // SharedPreferencesの練習
            // sp.edit().putString("DataString", "sample")
            // startActivity(share)
            // webViewの練習
            // webView.loadUrl("hhttps://www.google.com/")
            // loadWebpage()
        }

        btnMapAPI.setOnClickListener {
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
        }

        dialogButton.setOnClickListener {
//            var dataString = sp.getString("DataString", null)
            var dataString = "\"新規借入停止させていただきました。\n" +
                    "普通預金へご入金をお願いします。"
            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                .setTitle("【重要】フリーローンご返済のお願い")
                .setMessage(dataString)
                .setPositiveButton("OK", { dialog, which ->
                })
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
            val intent =
                builder.createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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

        btn_shuffle.setOnClickListener {
            var lastId: String = "3"
            determineId(lastId)
            lastId = id
            println(lastId)
        }

        btn_ipaddress.setOnClickListener {

            distanceBetween()
            // 東京駅〜大阪駅の距離　
            var number = distanceBetween()[0]
            var numbers = distanceBetween().toString()

            println(number)
            btn_ipaddress.text = distanceBetween().toString()
            println("distanceBetween")

        }


        // Activity遷移
        btnActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SubActivity::class.java)
            //生成したオブジェクトを引数に画面を起動！
            startActivity(intent)
        }

        // Fragment遷移
        btnFragment.setOnClickListener {
            val secondFragment = SecondFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_container, secondFragment)
            fragmentTransaction.commit()
        }

    }

    fun determineId(lastId: String) {

        val list: MutableList<Int> = mutableListOf()
        for (i in 1..6) {
            if (i != lastId.toInt()) {
                list.add(i)
            }
        }
        // シャッフルして、順番を変える
        list.shuffle()
        println(list[0])
        id = list[0].toString()
    }

    // ファイル保存時の挙動の中身
    private fun saveFile(file: String, str: String) {

        applicationContext.openFileOutput(file, Context.MODE_PRIVATE)
            .use { // MODE_WORLD_READABLE	他のアプリから読み取り可 MODE_WORLD_WRITEABLE	他のアプリから書込み可能 MODE_PRIVATE	そのアプリ内でのみ使用可能
                it.write(str.toByteArray())
            }

        //File(applicationContext.filesDir, file).writer().use {
        //    it.write(str)
        //}
    }

    private fun readFiles(file: String): String? {

        // to check whether file exists or not
        val readFile = File(applicationContext.filesDir, file)

        if (!readFile.exists()) {
            Log.d("debug", "No file exists")
            return null
        } else {
            return readFile.bufferedReader().use(BufferedReader::readText)
        }
    }

    // オフライン時の挙動の中身
    private fun isOnline(): Boolean {
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

    fun offline() { // オフライン の時はダイアログ
        if (!isOnline()) {
            val dialog = SimpleDialogFragment()
            dialog.show(supportFragmentManager, "simple")
        }
    }

}

fun distanceBetween(): FloatArray {
    var x: Double = 35.6809591
    var y: Double = 139.7673068
    var x2: Double = 34.7338219
    var y2: Double = 135.5014056

    // 東京駅　35.6809591 139.7673068
    // 大阪駅　34.7338219 135.5014056
    // セブン銀行東京駅一番街共同出張所　35.681591637257874, 139.7681923730127
    // 東京都千代田区丸の内１丁目９−１35.68159226092651, 139.76896529034997

    val results = FloatArray(3)

    Location.distanceBetween(x, y, x2, y2, results)
    return results

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