package com.example.yididiya.texttospeech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextToSpeech textToSpeech;
    private EditText editText;
    private Button btn;
    private SeekBar seekSpeed;
    private SeekBar seekPitch;
    private long back_press;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // default fragment container start with setting fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new settingFragment()).commit();
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();// rotetes the Hamburger drawer button

        // to listen to click events
        NavigationView navigationView = findViewById(R.id.nav_view); // Referencing the navigation view
        navigationView.setNavigationItemSelectedListener(this);

        // The Text To Speech code below
        btn = findViewById(R.id.speakBtn);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int myResult = textToSpeech.setLanguage(Locale.US);
                    if (myResult == TextToSpeech.LANG_MISSING_DATA || myResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("MSG", "language not supported");
                        Toast.makeText(getBaseContext(), "Unsupported Data Entered", Toast.LENGTH_SHORT).show();
                    } else {
                        btn.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Unsupported Data Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        Button stop_btn = findViewById(R.id.stop_btn);
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                } else {
                    Toast.makeText(getBaseContext(), "Engine is not Speaking", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Day Night Mode Setting
        //  Switch switch1=findViewById(R.id.switch_id);

    }

    public void speak() {
        seekPitch = findViewById(R.id.seekBar_pitch);
        seekSpeed = findViewById(R.id.seekBar_speed);
        editText = findViewById(R.id.edit_text1);

        String text = editText.getText().toString();
        // for progress bar
        //  Attention the error will happen when we didnot select adjestment in fragment section
        float pitch = (float) seekPitch.getProgress()/50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) seekSpeed.getProgress()/50;
        if (speed < 0.1) speed = 0.1f;
       textToSpeech.setPitch(pitch);
       textToSpeech.setSpeechRate(speed);
       textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }


    // Overriding the implemented interface
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_doc:
                Intent intent2 = new Intent(this, Documentation.class);
                startActivity(intent2);
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new documentFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new settingFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new aboutFragment()).commit();
                break;
            case R.id.nav_share:
                // Sharing the app code
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share_body = "body here";
                String shareIt = "your subject here";
                intent.putExtra(Intent.EXTRA_SUBJECT, share_body);
                intent.putExtra(Intent.EXTRA_TEXT, shareIt);
                startActivity(Intent.createChooser(intent, "share type"));
                break;
            case R.id.nav_send:
                Intent intents = new Intent(this, send_Activity.class);
                startActivity(intents);
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new aboutFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Do you want to exit?")
                    .setIcon(R.drawable.ic_info)
                    .setPositiveButton("exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            back();
                        }
                    }).show();

           /* back_press = System.currentTimeMillis();
           if (back_press + 2000 > System.currentTimeMillis()) {
                  super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            }*/
           }
        }
        public void back(){
            super.onBackPressed();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.items:
                Intent intent=new Intent(this,setting_activity.class);
                startActivity(intent);
                break;
            case R.id.help:
                Intent intent_help=new Intent(this,helpActivity.class);
                startActivity(intent_help);
                break;
        }
        return true;
    }

    // Activity Life Cycle And save State
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
