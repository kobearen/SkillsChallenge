package com.example.skillschallenge


import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiButton.setOnClickListener {
            resultApiText.text = ""
            HitAPITask().execute("https://api.punkapi.com/v2/beers")
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


    // ここからのエラーが解決できない状況です　2020/08/25
    inner class HitAPITask: AsyncTask<String, String, String>(){

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                return jsonText

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }

        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result == null) return

            resultApiText.text = result
        }
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

}