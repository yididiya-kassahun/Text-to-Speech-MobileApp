package com.example.yididiya.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebViewClient;

public class Documentation extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

         Toolbar toolbar=findViewById(R.id.docum_toolbar);
        //setSupportActionBar(toolbarz);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
        webView=findViewById(R.id.web_id);
        webView.loadUrl("https://developer.android.com/reference/android/speech/tts/TextToSpeech");
        webView.setWebViewClient(new WebViewClient()); //Load in app not external browser
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void home(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        long back_press=System.currentTimeMillis();
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            if (back_press + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            }
        }
    }

}
