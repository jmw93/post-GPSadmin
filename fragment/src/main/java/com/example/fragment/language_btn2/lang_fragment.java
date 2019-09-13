package com.example.fragment.language_btn2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.fragment.R;

public class lang_fragment extends Fragment {
    WebView webview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String address ="https://papago.naver.com/";
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment1,container,false);
        webview = view.findViewById(R.id.webview);
        WebSettings websettings = webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(address);

    return view;
    }
}
