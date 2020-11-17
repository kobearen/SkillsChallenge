package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    private val tag = "BloodPressure"
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realm = Realm.getDefaultInstance()

        findViewById<Button>(R.id.saveBtn).setOnClickListener {
            var max: Long = 0
            var min: Long = 0
            var pulse: Long = 0
            if (!maxEdit.text.isNullOrEmpty()) {
                max = maxEdit.text.toString().toLong()
            }
            if (!minEdit.text.isNullOrEmpty()) {
                min = minEdit.text.toString().toLong()
            }
            if (!pulseEdit.text.isNullOrEmpty()) {
                pulse = pulseEdit.text.toString().toLong()
            }
            realm.executeTransaction { // ───②
                val maxId = realm.where<BloodPress>().max("id") //───②
                val nextId = (maxId?.toLong() ?: 0L) + 1L //───②2
                val bloodPress = realm.createObject<BloodPress>(nextId) //───②3
                bloodPress.dateTime = Date()
                bloodPress.max = max
                bloodPress.min = min
                bloodPress.pulse = pulse
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show() //───②4
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
