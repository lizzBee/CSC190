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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends Activity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    Button forgetPass, createAccount, login;
    EditText username, pass;
    FirebaseAuth mAuth;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        username = findViewById(R.id.etUN);
        pass = findViewById(R.id.etPW);

        //forgetPass = (Button)findViewById(R.id.tvForgotPW);
        createAccount = findViewById(R.id.btnCreateNewAccount);
        login = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        //createAccount is a button
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String password = pass.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    username.setError("Email is Required!");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    pass.setError("Password is Required!");
                    return;
                }
                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Welcome to Book Bazaar", Toast.LENGTH_LONG).show();
                            //setContentView(R.layout.login);
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else {
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });

    }
}
