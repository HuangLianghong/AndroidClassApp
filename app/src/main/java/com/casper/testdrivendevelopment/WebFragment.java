package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        WebView webView = view.findViewById(R.id.web_view);

        //在当前视图打开
        webView.setWebViewClient(new WebViewClient());

        //一般允许JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //适应HTML5
        webView.getSettings().setDomStorageEnabled(true);
        //加载URL
        webView.loadUrl("http://www.baidu.com/");


        return view;
    }

}
