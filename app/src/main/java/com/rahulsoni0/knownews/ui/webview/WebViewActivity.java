package com.rahulsoni0.knownews.ui.webview;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.rahulsoni0.knownews.R;
import com.rahulsoni0.knownews.databinding.ActivityWebViewBinding;
import com.rahulsoni0.knownews.ui.activity.MainActivity;

public class WebViewActivity extends AppCompatActivity {
    private ActivityWebViewBinding binding;
    private String url;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        d = new Dialog(WebViewActivity.this);
        d.setContentView(R.layout.loadingdialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.setCancelable(false);
        d.show();

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            url = bundle.getString("url");
        }

        setupWebView();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebViewActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void setupWebView() {

        binding.webViewDetails.getSettings().setJavaScriptEnabled(true);
        binding.webViewDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        binding.webViewDetails.setWebViewClient(new WebViewClient() {

            ProgressBar dialog = new ProgressBar(WebViewActivity.this);

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        //enable responsive layout

        binding.webViewDetails.getSettings().setLoadWithOverviewMode(true);
        binding.webViewDetails.getSettings().setSupportZoom(true);
        binding.webViewDetails.getSettings().setBuiltInZoomControls(true);
        binding.webViewDetails.getSettings().setDisplayZoomControls(true);
        binding.webViewDetails.loadUrl(url);
        d.dismiss();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.webViewDetails.canGoBack()) {
            binding.webViewDetails.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);


    }
}