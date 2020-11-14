package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    FirebaseFirestore fStore;
    EditText email;
    Button send, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        email = findViewById(R.id.editTextTextEmailAddress3);
        send = findViewById(R.id.send_butt);
        back = findViewById(R.id.login_butt);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_email = email.getText().toString().trim();

                if(TextUtils.isEmpty(user_email)) {
                    email.setError("Email is Required!");
                    return;
                }
                mAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Password has been sent!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            //Toast.makeText(ForgotPassword.this, "No account associated with"  +user_email, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }

    });
}}