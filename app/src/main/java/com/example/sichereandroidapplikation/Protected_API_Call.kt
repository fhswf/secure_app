package com.example.sichereandroidapplikation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_insecure_communication_test_https.*
import org.json.JSONObject

class Protected_API_Call : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protected__a_p_i__call)

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/api/private"

        val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->

                    val obj = JSONObject(response)
                    val message = obj.getString("message").toString()
                    textView_api.text = message

                },
                { textView_api.text = "Verbindung zum Server konnte nicht hergestellt werden!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}