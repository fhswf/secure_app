package com.example.secure_app

/**
 * Diese Klasse implementiert die Speicherung eines Teststrings
 * mit Hilfe der Library EncryptedSharedPreferences.
 *
 * @author Marcel Hopp
 */

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_shared_prefs_test.*
import org.w3c.dom.Text

class EncryptedSharedPreferencesTestActivity : AppCompatActivity() {

    private val encrypted_shared_prefs = "encrypted_shared_prefs"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var input_text: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs_test)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "EncryptedSharedPreferences"

        input_text = findViewById(R.id.eingabe_string_shared_prefs)

        encryptSharedPrefs()
        deleteData()
        loadData()

        /** Text-Watcher-Objekt - prüft, ob das Eingabefeld leer ist.
         *  Falls nein, wird der Speicherbutton freigeschaltet.
         */
        input_text.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                speicherbutton_shared_prefs.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        speicherbutton_shared_prefs.setOnClickListener{
            saveData()
        }
    }

    /**
     * Erstellt einen neuen Masterkey zur Ver-/ und Entschlüsselung
     * und initialisiert eine neue Instanz der EncryptedSharedPreferences-Klasse
     */
    private fun encryptSharedPrefs()
    {
        /** Erstellt einen neuen Masterkey zur Ver-/und Entschlüsselung */
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        /** Initalisiert eine neue Instanz von EncryptedSharedPreferences */
        sharedPreferences = EncryptedSharedPreferences.create(
                encrypted_shared_prefs,
                masterKeyAlias,
                applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Speichert den übergebenen String aus der GUI in den SharedPreferences.
     */
    private fun saveData()
    {
        val insertedText = eingabe_string_shared_prefs.text.toString()
        ausgabe_shared_prefs.text = insertedText

            val editor = sharedPreferences.edit()
            editor.apply{
                putString("String_Key", insertedText)
            }.apply()

            Toast.makeText(this, "String gespeichert", Toast.LENGTH_SHORT).show()
    }

    /**
     * Lädt den zuvor übergebenen String entschlüsselt und zeigt diesen in der Activity an.
     */
    private fun loadData()
    {
        val savedString = sharedPreferences.getString("String_Key", null)
        ausgabe_shared_prefs.text = savedString
    }

    /**
     * Löscht den String, der vorher übergeben wurde aus den SharedPreferences.
     */
    private fun deleteData()
    {
        sharedPreferences.edit().remove("String_Key").commit()
    }
}