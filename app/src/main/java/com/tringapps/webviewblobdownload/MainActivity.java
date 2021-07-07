package com.tringapps.webviewblobdownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView browser;
    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    int permsRequestCode = 200;

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        if (permsRequestCode == 200) {
            boolean storageGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(storageGranted) {
                browser.loadUrl("https://secure8.convio.net/jdrf3itc/site/SPageServer?pagename=my_events&_ga=2.195897514.780724180.1625652739-696323456.1625652739");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        browser = findViewById(R.id.browser);
        browserSettings();
        requestPermissions(perms, permsRequestCode);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void browserSettings() {
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> browser.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url)));
        browser.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        browser.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        browser.getSettings().setDatabaseEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        browser.getSettings().setPluginState(WebSettings.PluginState.ON);
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                return false;
            }
        });
    }
}