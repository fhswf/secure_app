package com.example.secure_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Diese Klasse implementiert die Prüfung nach einer vorhandenen Internetverbindung.
 *
 * @author: Marcel Hopp
 */

public class Common {

    /**
     * Funktion, die eine vorhandene Internetverbindung prüft.
     * @param context
     * @return true, sobald eine Internetverbindung festgestellt wurde.
     */
    public static boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager!=null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
