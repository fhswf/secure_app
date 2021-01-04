package com.example.sichereandroidapplikation
/**
Diese Klasse implemementiert die Verbindung zum Backend. Hierfür wird Volley genutzt.
Mit Hilfe von Volley wird ein GET-Request zum Backend gesendet, welches die Daten im JSON-Format bereitstellt.
 */

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_insecure_communication_test_https.*
import kotlinx.android.synthetic.main.activity_shared_prefs_test.*
import org.json.JSONObject


class insecure_communication_test_https : AppCompatActivity() {

    var networkChangeListener = NetworkChangeListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insecure_communication_test_https)

        /** API aufrufen, sobald Button gedrückt*/
        button_api.setOnClickListener{
            connectToAPI()
        }

    }

    /**
     * Funktion zum Aufruf der API des Backends.
     */
    private fun connectToAPI()
    {
        val queue = Volley.newRequestQueue(this)
        //URL zur API
        val url = "https://jupiter.fh-swf.de/secureapp/api/public/uhrzeit"

        //Volley-Request an die API mit Ausgabe in der Activity
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
                    textView_api.text = "Heute ist der $tag-$monat-$jahr. \n Die aktuelle Serveruhrzeit ist $stunden:$minuten:$sekunden."

                },
                { textView_api.text = "Verbindung zum Server konnte nicht hergestellt werden!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    override fun onStart() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }
}