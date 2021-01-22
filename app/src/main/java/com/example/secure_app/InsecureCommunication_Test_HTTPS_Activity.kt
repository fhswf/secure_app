package com.example.secure_app
/**
 * Diese Klasse implemementiert die Verbindung zum Backend. Hierfür wird Volley genutzt.
 * Mit Hilfe von Volley wird ein GET-Request zum Backend gesendet, welches die Daten im JSON-Format bereitstellt
 * und im TextView ausgibt.
 *
 * @author Marcel Hopp
 */

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_insecure_communication_test_https.*
import org.json.JSONObject


class InsecureCommunication_Test_HTTPS_Activity : AppCompatActivity() {

    var networkChangeListener = NetworkChangeListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insecure_communication_test_https)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        /** API aufrufen, sobald Button gedrückt*/
        button_api.setOnClickListener{
            progressBar.visibility = View.VISIBLE
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
        val url = "https://jupiter.fh-swf.de/secureapp/api/public"

        //Volley-Request an die API mit Ausgabe in der Activity
        val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    progressBar.visibility = View.INVISIBLE

                    val obj = JSONObject(response)
                    val jahr = obj.getString("jahr")
                    val monat = obj.getString("monat")
                    val tag = obj.getString("tag")
                    val stunden = obj.getString("stunden")
                    val minuten = obj.getString("minuten")
                    val sekunden = obj.getString("sekunden")
                    val wochentag = obj.getString("wochentag")
                    textView_api.text = "Sichere Verbindung zum Backend hergestellt. \n\n Heute ist $wochentag, der $tag.$monat.$jahr. \n Die aktuelle Serveruhrzeit ist $stunden:$minuten:$sekunden."
                },
                {
                    progressBar.visibility = View.INVISIBLE
                    textView_api.text = "Verbindung zum Server konnte nicht hergestellt werden!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    /**
     * Prüft zur Laufzeit, ob eine Internetverbindung besteht und gibt ggf. eine Meldung aus.
     */
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

