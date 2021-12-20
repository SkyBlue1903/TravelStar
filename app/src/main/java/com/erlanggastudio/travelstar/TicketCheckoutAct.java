package com.erlanggastudio.travelstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy_ticket;
    ImageView btnmines, btnpls;
    TextView textjumlahtiket, texttotalharga, textmybalance, nama_wisata, lokasi, ketentuan;
    Integer valuejumlahtiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Integer nomor_transaksi = new Random().nextInt();

    String date_wisata = "";
    String time_wisata = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);
        getUsernameLocal();

//        MENGAMBIL DATA DARI INTENT
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru  = bundle.getString("jenis_tiket");


//        KOMPONEN
        btnmines = findViewById(R.id.btnmines);
        btnpls = findViewById(R.id.btnpls);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);


//        SETTING NILAI
        textjumlahtiket.setText(valuejumlahtiket.toString());


//        BY DEFAULT DISABLE TOMBOL MINUS
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);

//        MENGAMBIL DATA USER DARI FIREBASE
        reference2 = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("USD " + mybalance + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //        MENGAMBIL DATA DARI FIREBASE BERDASARKAN INTENT                                    ------------------------------------------------------------------ jenis_tiket_baru
        reference = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                OVERWRITE DATA YANG ADA DI ACTIVITY ITU
                nama_wisata.setText(String.valueOf(dataSnapshot.child("nama_wisata").getValue()));
                lokasi.setText(String.valueOf(dataSnapshot.child("lokasi").getValue()));
                ketentuan.setText(String.valueOf(dataSnapshot.child("ketentuan").getValue()));
                date_wisata = (String.valueOf(dataSnapshot.child("date_wisata").getValue()));
                time_wisata = (String.valueOf(dataSnapshot.child("time_wisata").getValue()));
                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());


                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("USD " + valuetotalharga + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnpls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahtiket += 1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket > 1) {
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("USD " + valuetotalharga + "");
                if (valuetotalharga > mybalance) {
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_buy_ticket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahtiket -= 1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket < 2) {
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("USD " + valuetotalharga + "");
                if (valuetotalharga < mybalance) {
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_buy_ticket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                }

            }
        });



//        TOMBOL PENDUKUNG
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtohome = new Intent(TicketCheckoutAct.this, TicketDetailAct.class);
                startActivity(backtohome);
            }
        });


        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        MENYIMPAN DATA USER KE FIREBASE DAN MEMBUAT TABEL BARU "MyTickets"
                reference3 = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahtiket.toString() + " Tiket");
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);


                        Intent gotosuccessbuyticket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotosuccessbuyticket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                UPDATE SISA TRAVELPAY
                reference4 = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(Integer.valueOf(sisa_balance));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences  = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}