package com.sujin.hamrobazar.termsandconditions;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.sujin.hamrobazar.R;

public class PostingActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        webView = findViewById(R.id.webView);

        webView.loadUrl("https://hamrobazaar.com/postrules.html");
    }

}
