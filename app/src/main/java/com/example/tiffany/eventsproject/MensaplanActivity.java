package com.example.tiffany.eventsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tiffany.eventsproject.R;

public class MensaplanActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaplan);

        final ProgressBar pbar;
        final TextView txtview = (TextView)findViewById(R.id.lblProgressBar);
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        try {
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl("https://www.stw-ma.de/Essen+_+Trinken/Men%C3%BCpl%C3%A4ne/Hochschule+Mannheim.html");
        }catch(Exception e){
            TextView lblMenuNotAvailiable = (TextView) findViewById(R.id.lblMenuNotAvailiable);
            lblMenuNotAvailiable.setVisibility(View.VISIBLE);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && pbar.getVisibility() == ProgressBar.GONE){
                    pbar.setVisibility(ProgressBar.VISIBLE);
                    txtview.setVisibility(View.VISIBLE);
                }

                pbar.setProgress(progress);
                if(progress == 100) {
                    pbar.setVisibility(ProgressBar.GONE);
                    txtview.setVisibility(View.GONE);
                }
            }
        });
    }
}
