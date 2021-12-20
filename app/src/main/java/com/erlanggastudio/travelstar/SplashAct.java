package com.erlanggastudio.travelstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btm_to_top;
    ImageView app_logo;
    TextView app_subtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        MEMUAT ANIMASI
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btm_to_top = AnimationUtils.loadAnimation(this, R.anim.btm_to_top);

//        MEMUAT ELEMEN
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);

//        MENJALANKAN ANIMASI
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btm_to_top);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gohome = new Intent(SplashAct.this, GetStartedAct.class);
                startActivity(gohome);
                finish();
            }
        }, 2000);
    }
}