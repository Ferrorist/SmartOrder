package com.cookandroid.registerlogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class action_web_freg extends Fragment {
    private View view;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.web_freg,container,false);

        webView = (WebView)view.findViewById(R.id.action_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=36");
//        webView.loadUrl("https://www.naver.com");
        return view;
}
}
