package com.example.skillschallenge


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import java.io.BufferedReader
import java.io.File

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

        // ファイル書き出しと保存
        val mbtnSaveFile = findViewById<Button>(R.id.btnSaveFile)
        mbtnSaveFile.setOnClickListener {
                // Save file  // get string contents of EditText
                val meditFileSave = findViewById<EditText>(R.id.editFileSave)
                val contents = meditFileSave.text.toString()
            val mtextView = findViewById<TextView>(R.id.textView)
                if (contents.isNotEmpty()) {
                    saveFile("fileName", contents)
                    mtextView.text =  getString(R.string.saved)
                } else {
                    mtextView.text = getString(R.string.no_text)
                }
        }

        // ファイル読み取り
        val mbtnReadFile = findViewById<Button>(R.id.btnReadFile)
        mbtnReadFile.setOnClickListener{
            val str = readFiles("fileName")
            val mtextView = findViewById<TextView>(R.id.textView)
            if (str != null) {
                mtextView.text = str
            } else {
                mtextView.text = getString(R.string.no_text)
            }
        }

        val mBtn = findViewById<Button>(R.id.btn_fragment)
        mBtn.setOnClickListener {
            val webViewFragment = WebViewFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_container, webViewFragment)
            fragmentTransaction.commit()

            // SharedPreferencesの練習
            // sp.edit().putString("DataString", "sample")
            // startActivity(share)
            // webViewの練習
            // webView.loadUrl("hhttps://www.google.com/")
            // loadWebpage()
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

    // ファイル保存時の挙動の中身
    private fun saveFile(file: String, str: String) {

        applicationContext.openFileOutput(file, Context.MODE_PRIVATE).use { // MODE_WORLD_READABLE	他のアプリから読み取り可 MODE_WORLD_WRITEABLE	他のアプリから書込み可能 MODE_PRIVATE	そのアプリ内でのみ使用可能
            it.write(str.toByteArray())
        }

        //File(applicationContext.filesDir, file).writer().use {
        //    it.write(str)
        //}
    }
    private fun readFiles(file: String): String? {

        // to check whether file exists or not
        val readFile = File(applicationContext.filesDir, file)

        if(!readFile.exists()){
            Log.d("debug","No file exists")
            return null
        }
        else{
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