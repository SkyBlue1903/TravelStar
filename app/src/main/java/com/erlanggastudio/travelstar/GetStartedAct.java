package com.erlanggastudio.travelstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStartedAct extends AppCompatActivity {

//    Mendeklarasikan tombol sign in
    Button btn_sign_in, btn_new_acount_create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

//        Membuat tombol sign in berfungsi
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_acount_create = findViewById(R.id.btn_new_acount_create);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v){
            Intent gotosign = new Intent(GetStartedAct.this, SignInAct.class);
            startActivity(gotosign);
        }

        });

        btn_new_acount_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}