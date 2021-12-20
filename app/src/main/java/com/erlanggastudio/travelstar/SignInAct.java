package com.erlanggastudio.travelstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        TOMBOL TOMBOL NYA
        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Memuat...");

                final String username = xusername.getText().toString();
                String password = xpassword.getText().toString();


                reference = FirebaseDatabase.getInstance("https://travelstar-proyek-akhir-ppb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(username);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
//                            CEK PASSWORD UDAH BENER APA BELUM DAN UDAH SAMA YANG DIINPUTIN PA BLM
                            String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                            if(password.equals(passwordFromFirebase)) {
//                                SIMPAN DATA DI PENYIMPANAN LOKAL
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, xusername.getText().toString());
                                editor.apply();

//                                PINDAH ACTIVITY SETELAH DISIMPAN
                                Toast.makeText(getApplicationContext(), "Selamat datang!", Toast.LENGTH_SHORT).show();
                                Intent gotohome = new Intent(SignInAct.this, HomeAct.class);
                                startActivity(gotohome);
                            } else {
                                Toast.makeText(getApplicationContext(), "Kata sandi salah", Toast.LENGTH_SHORT).show();
                                btn_sign_in.setEnabled(true);
                                btn_sign_in.setText("MASUK");
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Akun tidak ditemukan, silakan mendaftar.", Toast.LENGTH_SHORT).show();
                            btn_sign_in.setEnabled(true);
                            btn_sign_in.setText("MASUK");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Masalah koneksi, silakan coba lagi nanti.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}