package com.example.dooriot

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import java.text.SimpleDateFormat
import java.time.Instant

class RecyclerRegistryAdapter(private val jsonArray: JsonArray) : RecyclerView.Adapter<RecyclerRegistryAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView){
        val username = rootView.findViewById(R.id.date) as TextView
        val email = rootView.findViewById(R.id.door) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerRegistryAdapter.ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = jsonArray[position].asJsonObject["fecha"].asString
        val formatter = Instant.parse(date)

        val idPlusUsername = jsonArray[position].asJsonObject["npuerta"].asString + " - " + jsonArray[position].asJsonObject["ID"].asString
        holder.username.text = idPlusUsername
        holder.email.text = formatter.toString().substring(0, 10)
    }

    override fun getItemCount() = jsonArray.size()
}