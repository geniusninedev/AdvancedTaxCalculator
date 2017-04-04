package com.nineinfosys.android.advancedtaxcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Dev on 24-03-2017.
 */

public class AdvancedTaxHelp extends AppCompatActivity {
    WebView Introwebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Introwebview = (WebView) findViewById(R.id.help);
        WebSettings IntroWebSettings = Introwebview.getSettings();
        IntroWebSettings.setBuiltInZoomControls(true);
        IntroWebSettings.setJavaScriptEnabled(true);
        Introwebview.setWebViewClient(new AdvancedTaxHelp.WebViewClient());
        Introwebview.loadUrl("file:///android_res/raw/advancedtaxhtml.html");
    }


    public class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
