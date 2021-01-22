package com.example.secure_app;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementiert eine SQL-Cipher-Datenbank-Hilfsklasse.
 * Hier werden sämtliche SQL-Operationen durchgeführt.
 *
 * @author Marcel Hopp
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;
    SharedPreferences sharedPreferences = null;
    Context context;

    private static final int DATABASE_VER = 1;
    public static final String DATABASE_NAME = "encrypted_database.db";

    public static final String TABLE_NAME = "CONTACTS";
    public static final String COLUMN_EMAIL = "EMAIL";

    public static String PASS_PHRASE = "";

    private static final String SQL_CREATE_QUERY = "CREATE TABLE " +TABLE_NAME+" ("+COLUMN_EMAIL+" TEXT PRIMARY KEY)";
    private static final String SQL_DELETE_QUERY = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        this.context = context;
    }

    static public synchronized DBHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBHelper(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initialize_encrypted_shared_preferences();
        db.execSQL(SQL_CREATE_QUERY);
        PASS_PHRASE = sharedPreferences.getString("password","");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }

    /**
     * Fügt eine neue E-Mail-Adresse ein.
     * @param email gewünschte E-Mail-Adresse
     */
    public void insertNewEmail(String email) {
        initialize_encrypted_shared_preferences();
        PASS_PHRASE = sharedPreferences.getString("password","");

        SQLiteDatabase db = instance.getWritableDatabase(PASS_PHRASE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Updatet eine E-Mail-Adresse.
     * @param oldEmail zu ersetzende E-Mail-Adresse
     * @param newEmail neue E-Mail-Adresse
     */
    public void updateEmail(String oldEmail, String newEmail) {
        initialize_encrypted_shared_preferences();
        PASS_PHRASE = sharedPreferences.getString("password","");
        SQLiteDatabase db = instance.getWritableDatabase(PASS_PHRASE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);
        db.update(TABLE_NAME, values, COLUMN_EMAIL + "='" + oldEmail + "'", null);
        db.close();
    }

    /**
     * Löscht eine E-Mail-Adresse.
     * @param email gewünschte E-Mail-Adresse
     */
    public void deleteEmail(String email) {
        initialize_encrypted_shared_preferences();
        PASS_PHRASE = sharedPreferences.getString("password","");
        SQLiteDatabase db = instance.getWritableDatabase(PASS_PHRASE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        db.delete(TABLE_NAME, COLUMN_EMAIL + "='" + email + "'", null);
        db.close();
    }

    /**
     * Gibt alle E-Mail-Adressen der Datenbank aus.
     */
    public List<String> getAllEmail() {
        initialize_encrypted_shared_preferences();
        PASS_PHRASE = sharedPreferences.getString("password","");
        SQLiteDatabase db = instance.getWritableDatabase(PASS_PHRASE);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_NAME), null);
        List<String> emails = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                emails.add(email);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return emails;
    }

    /**
     * Funktion zum Initialisieren der EncryptedSharedPreferences.
     */
    private void initialize_encrypted_shared_preferences()
    {
        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
