package com.example.sichereandroidapplikation

/**
 * Diese Klasse implementiert die Speicherung eines Teststrings
 * mit Hilfe der Library EncryptedSharedPreferences.
 */

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_shared_prefs_test.*

class SharedPrefsTest : AppCompatActivity() {

    private val encrypted_shared_prefs = "encrypted_shared_prefs"
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs_test)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        encryptSharedPrefs()

        loadData()

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
        //Erstellt einen neuen Masterkey zur Ver-/und Entschlüsselung
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        //Initalisiert eine neue Instanz von EncryptedSharedPreferences
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

        if(insertedText.equals(""))
        {
            Toast.makeText(this, "Kein String übergeben!", Toast.LENGTH_SHORT).show();
        }
        else {
            val editor = sharedPreferences.edit()
            editor.apply{
                putString("String_Key", insertedText)
            }.apply()

            Toast.makeText(this, "String gespeichert", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Lädt den zuvor übergebenen String entschlüsselt und zeigt diesen in der Activity an.
     */
    private fun loadData()
    {
        val savedString = sharedPreferences.getString("String_Key", null)
        ausgabe_shared_prefs.text = savedString
    }
}
