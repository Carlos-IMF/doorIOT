package com.example.dooriot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
   // var SPREAD_SHEET_ID = "1zCI8_vLPaHqtKZ2mDxsoGo-Dy4kK8VcNiGlErxWXYbg"
      var SPREAD_SHEET_ID = "15qwMYtltFK0Y7zFZwp4_OtryEuuqNsbNt13bw12QzaI"
      var TABLE_REGISTRY = "Hoja 1"
    var sheetInJsonURL = "https://script.google.com/macros/s/AKfycbxucxlWrMloRZymvKKREnNlAY-c-FXVNHVru4y2duPozhOffPLUdDD4bquTBHxq85m1/exec?spreadsheetId=$SPREAD_SHEET_ID&sheet="
    var LOG_TAG = "IMF"
    private lateinit var recyclerRegistryAdapter: RecyclerRegistryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getRegistry()

        val button = findViewById<Button>(R.id.BtnStart)
        val webhook = findViewById<WebView>(R.id.webload)

        button.setOnClickListener {
            Toast.makeText(this, "solicitud enviada", Toast.LENGTH_SHORT).show()

            webhook.loadUrl("https://maker.ifttt.com/trigger/new_user/json/with/key/df1zdR5ZjFFQuEIa0XLG0-")
        }


    }
    fun initList(){
        recyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        llm.reverseLayout = true
        llm.stackFromEnd = true

        recyclerView.layoutManager = llm

        progressBar.visibility = View.GONE
    }
    fun getRegistry(){
        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, sheetInJsonURL + TABLE_REGISTRY, null,
            { response ->
                Log.i(LOG_TAG, "Response is: $response")

                val gson = Gson()
                val jsonArray: JsonArray = gson.fromJson<JsonArray>(response.toString(), JsonArray::class.java)

                for (element: JsonElement in jsonArray){
                    Log.i(LOG_TAG, element.asJsonObject.toString())
                }

                recyclerRegistryAdapter = RecyclerRegistryAdapter(jsonArray)
                initList()
                recyclerView.adapter = recyclerRegistryAdapter
            },
            { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "That didn't work!")
            }
        )
        queue.add(jsonArrayRequest)
    }
}