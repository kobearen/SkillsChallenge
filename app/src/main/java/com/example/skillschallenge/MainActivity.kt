package com.example.skillschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?):Boolean{
        when(item?.itemId) {
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
        }
        return super.onOptionsItemSelected(item)
    }
}