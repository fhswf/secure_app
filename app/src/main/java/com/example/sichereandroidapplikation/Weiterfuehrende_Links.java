package com.example.sichereandroidapplikation;

/*
Diese Klasse sorgt dafür, dass die einzelnen Links geöffnet werden.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Weiterfuehrende_Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView link_fh_homepage, link_gawron_homepage, link_kubek_homepage, link_owasp_homepage,
                link_androidsec_homepage, link_auth0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weiterfuehrende__links);

        link_fh_homepage = (TextView) findViewById(R.id.link_FH_tv);
        link_fh_homepage.setMovementMethod(LinkMovementMethod.getInstance());

        link_gawron_homepage = (TextView) findViewById(R.id.link_gawon_tv);
        link_gawron_homepage.setMovementMethod(LinkMovementMethod.getInstance());

        link_kubek_homepage = (TextView) findViewById(R.id.link_kubek_tv);
        link_kubek_homepage.setMovementMethod(LinkMovementMethod.getInstance());

        link_owasp_homepage = (TextView) findViewById(R.id.link_owasp_tv);
        link_owasp_homepage.setMovementMethod(LinkMovementMethod.getInstance());

        link_androidsec_homepage = (TextView) findViewById(R.id.link_android_security_tv);
        link_androidsec_homepage.setMovementMethod(LinkMovementMethod.getInstance());

        link_auth0 = (TextView) findViewById(R.id.link_auth0_tv);
        link_auth0.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
