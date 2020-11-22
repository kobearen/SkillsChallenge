package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults


class CustomRecyclerViewAdapter(realmResults: RealmResults<BloodPress>): RecyclerView.Adapter<ViewHolder>(){
    private val rResults:RealmResults<BloodPress> = realmResults
    override fun onCreateViewHolder(parent: ViewGroup, position:Int):ViewHolder{ //───①
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_result,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount():Int{ // ───③
        return rResults.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder:ViewHolder, position:Int){ //───②
        val bloodPress= rResults[position]
        holder.dateText?.text = DateFormat.format ("yyyy/MM/ddkk:mm",bloodPress?.dateTime)
        holder.minMaxText?.text = "${bloodPress?.max.toString()}/${bloodPress?.min.toString()}"
        holder.pulseText?.text = bloodPress?.pulse.toString()
        holder.itemView.setBackgroundColor(if (position % 2 == 0) Color.LTGRAY else Color.WHITE) //───②-1
    }
}