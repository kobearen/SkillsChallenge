package com.example.skillschallenge

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 長押しした時の挙動
        registerForContextMenu(menuImage)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (menuText.text.isNotEmpty()) {
            menuInflater.inflate(R.menu.context, menu)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.home -> {
                //───①
                menuImage.setImageResource(R.drawable.okasan)
                menuText.text = ""
                return true
            }
            R.id.greencurry -> {
                menuImage.setImageResource(R.drawable.nizakana)
                menuText.text = getString(R.string.greencurry_text)
                return true
            }
            R.id.katsucurry -> {
                menuImage.setImageResource(R.drawable.sasimi)
                menuText.text = getString(R.string.katsucurry_text)
                return true
            }
            R.id.oden -> {
                menuImage.setImageResource(R.drawable.pizza)
                menuText.text = getString(R.string.tamoricurry_text)
                return true
            }
            R.id.katsucurry -> {
                menuImage.setImageResource(R.drawable.surmon)
                menuText.text = getString(R.string.katsucurry_text)
                return true
            }
            R.id.mail -> {
                val subject = getString(R.string.app_name)
                val text = "${menuText.text}がたべたい！"
                val uri = Uri.fromParts("mailto", "kanehiro@gmail.com", null)
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, text)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.sms -> {
                val text = "${menuText.text}がたべたい！"
                val uri = Uri.fromParts("smsto", "99999999999", null)
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", text)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}