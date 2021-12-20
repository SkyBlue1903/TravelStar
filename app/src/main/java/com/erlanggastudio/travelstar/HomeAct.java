package com.erlanggastudio.travelstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.github.florent37.shapeofview.shapes.CircleView;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_ticket_pisa, btn_ticket_torri, btn_ticket_pagoda, btn_ticket_candi, btn_ticket_sphinx, btn_ticket_monas;
    CircleView btn_to_profile;
    TextView btn_to_profile_2, user_balance, nama_lengkap;
    ImageView photo_home_user;

    ImageView traveltips1, traveltips2, traveltips3, traveltips4, traveltips5;
    ImageView author1, author2, author3;

//    DATABASE
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        PANGGIL PENYIMPANAN LOKAL
        getUsernameLocal();

        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);

//        TOMBOL TIKET WISATANYA
        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_candi = findViewById(R.id.btn_ticket_candi);
        btn_ticket_sphinx = findViewById(R.id.btn_ticket_sphinx);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);


//        PANGGIL DATABASE FIREBASE
        reference = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                user_balance.setText("USD " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        ACTION TOMBOL TIKET WISATA
        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                // meletakkan data pada intent
                gototicket.putExtra("jenis_tiket", "Pisa");
                startActivity(gototicket);
            }
        });

        btn_ticket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicket.putExtra("jenis_tiket", "Torri");
                startActivity(gototicket);
            }
        });

        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicket.putExtra("jenis_tiket", "Pagoda");
                startActivity(gototicket);
            }
        });

        btn_ticket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicket.putExtra("jenis_tiket", "Candi");
                startActivity(gototicket);
            }
        });

        btn_ticket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicket.putExtra("jenis_tiket", "Sphinx");
                startActivity(gototicket);
            }
        });

        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicket.putExtra("jenis_tiket", "Monas");
                startActivity(gototicket);
            }
        });



        btn_to_profile = findViewById(R.id.btn_to_profile);
        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_to_profile_2 = findViewById(R.id.btn_to_profile_2);
        btn_to_profile_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile2 = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoprofile2);
            }
        });


//        TRAVEL TIPS
        traveltips1 = findViewById(R.id.traveltips1);
        traveltips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://wisatamuda.com/tempat-wisata-paris/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        traveltips2 = findViewById(R.id.traveltips2);
        traveltips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://kumparan.com/kumparantravel/mengenal-lebih-dekat-menara-miring-dari-italia-yang-diakui-unesco");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        traveltips3 = findViewById(R.id.traveltips3);
        traveltips3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.99.co/blog/indonesia/sejarah-candi-borobudur-lengkap/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        traveltips4 = findViewById(R.id.traveltips4);
        traveltips4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.indonesia.travel/id/id/destinasi/java/dki-jakarta");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        traveltips5 = findViewById(R.id.traveltips5);
        traveltips5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.tsunagujapan.com/id/top-12-most-beautiful-torii-gates-in-japan/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//        TRAVEL AUTHOR
        author1 = findViewById(R.id.author1);
        author1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://anugrahangga.site/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        author2 = findViewById(R.id.author2);
        author2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.erlanggaitunesshop.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        author3 = findViewById(R.id.author3);
        author3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://wa.me/6282220571357/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences  = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}