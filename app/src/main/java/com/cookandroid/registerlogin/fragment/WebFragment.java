package com.cookandroid.registerlogin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cookandroid.registerlogin.R;


public class WebFragment extends Fragment {
    private View view;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fregment_web,container,false);

        webView = (WebView)view.findViewById(R.id.action_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true); //  webView 확대/축소 기능
        webView.getSettings().setSupportZoom(true); //  webView 확대/축소 기능
        webView.getSettings().setDisplayZoomControls(false); // webView 확대/축소 컨트롤박스 제거
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://coop.knu.ac.kr/sub03/sub01_01.html?shop_sqno=36");
//        webView.loadUrl("https://www.naver.com");
        return view;
}
}
