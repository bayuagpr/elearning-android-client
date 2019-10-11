package com.elearning.client.view.dosen.hasil.editor;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.client.R;

public class PDFActivity extends AppCompatActivity{
//    RemotePDFViewPager remotePDFViewPager;
//    PDFPagerAdapter pdfAdapter;
WebView webView;
    String attachmentMateri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initDataIntent();
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+attachmentMateri);
    }
    private void showLongToast(final String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
//

    private void initDataIntent() {
        Intent intent= getIntent();
        attachmentMateri = intent.getStringExtra("attachment_materi");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
