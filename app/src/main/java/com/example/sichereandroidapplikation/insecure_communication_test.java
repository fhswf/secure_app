package com.example.sichereandroidapplikation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link insecure_communication_test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class insecure_communication_test extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public insecure_communication_test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment insecure_communication_test.
     */
    // TODO: Rename and change types and number of parameters
    public static insecure_communication_test newInstance(String param1, String param2) {
        insecure_communication_test fragment = new insecure_communication_test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            //Initialisiert einen WebView in einem Fragment
            View v = inflater.inflate(R.layout.fragment_insecure_communication_test, container, false);
            WebView webView = (WebView)v.findViewById(R.id.webView_communication);
            //Deaktiviert JavaScript im WebView
            webView.getSettings().setJavaScriptEnabled(false);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl("http://10.0.2.2:3000/insecure_communication_test");

            return v;

    }
}