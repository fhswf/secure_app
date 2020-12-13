package com.example.sichereandroidapplikation

import android.content.Context
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

        encryptSharedPrefs()

        loadData()

        speicherbutton_shared_prefs.setOnClickListener{
            saveData()
        }
    }

    // Diese Funktion stellt die Verschlüsselungsoperationen für den übergebenen String bereit
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

    //Diese Funktion speichert den übergebenen String
    private fun saveData()
    {
        val insertedText = eingabe_string_shared_prefs.text.toString()
        ausgabe_shared_prefs.text = insertedText

       // val sharedPreferences = getSharedPreferences(encrypted_shared_prefs, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("String_Key", insertedText)
        }.apply()

        Toast.makeText(this, "String gespeichert", Toast.LENGTH_SHORT).show()
    }

    //Diese Funktion lädt den zuletzt übergebenen String aus den Shared_Prefs
    private fun loadData()
    {
        //val sharedPreferences = getSharedPreferences(encrypted_shared_prefs, Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("String_Key", null)
        ausgabe_shared_prefs.text = savedString
    }
}
