package com.example.yididiya.texttospeech;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

public class send_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ImageButton sendit=findViewById(R.id.compose);
        sendit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.send_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void  sendEmail(){
        Log.i("Send email","");
        String[] to={""};
        String[] cc={""};
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_CC,cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Email message here");
        try{
            startActivity(Intent.createChooser(emailIntent,"Send mail...."));
            finish();
            Log.i("Finished sending email","");
            Toast.makeText(getApplicationContext(),"Email send successfuly",Toast.LENGTH_SHORT).show();
        }catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(send_Activity.this,"There is no email client installed..",Toast.LENGTH_SHORT).show();
        }

    }
}
