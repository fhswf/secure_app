package com.example.sichereandroidapplikation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_insecure_communication_test_https.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.StringBuilder


class insecure_communication_test_https : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insecure_communication_test_https)

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/api/public/uhrzeit"

        val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->

                    val obj = JSONObject(response)
                    val jahr = obj.getInt("jahr").toString()
                    val monat = obj.getInt("monat").toString()
                    val tag = obj.getInt("tag").toString()
                    val stunden = obj.getInt("stunden").toString()
                    val minuten = obj.getInt("minuten").toString()
                    val sekunden = obj.getInt("sekunden").toString()
                    textView_api.text = "Es ist der $tag-$monat-$jahr. \n Die aktuelle Serveruhrzeit ist $stunden:$minuten:$sekunden."

                },
                { textView_api.text = "Verbindung zum Server konnte nicht hergestellt werden!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}