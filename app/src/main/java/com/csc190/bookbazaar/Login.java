package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends Activity {

    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button forgetPass, createAccount, login;
        final EditText username, password;

        username = (EditText) findViewById(R.id.etUN);
        password = (EditText) findViewById(R.id.etPW);

        //forgetPass = (Button)findViewById(R.id.tvForgotPW);
        createAccount = (Button)findViewById(R.id.btnCreateNewAccount);
        login = (Button)findViewById(R.id.btnLogin);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");
                setContentView(R.layout.registration);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("user");

                final String userNm = username.getEditableText().toString();
                final String pass = password.getEditableText().toString();

                if (userNm.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter your Username and Password", Toast.LENGTH_LONG).show();
                } else {

                    FirebaseDatabase.getInstance().getReference().child("User").child(userNm)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String pass = password.getEditableText().toString();
                                    // Get user information
                                    if (dataSnapshot.exists()){
                                        // the user does exist within the system
                                        User us = dataSnapshot.getValue(User.class);
                                        String userName = us.getHofstraID();
                                        String passWord = us.getPass();
                                        if (pass.equals(passWord)) {
                                            // if it matches, login successful (redirect to home eventually)
                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getBaseContext(), Profile.class);
                                            intent.putExtra("EXTRA_SESSION_ID", userNm);
                                            //startActivity(intent);

                                            //setContentView(R.layout.profile);
                                        } else {
                                            // if it doesn't match
                                            Toast.makeText(getApplicationContext(), "Username and Password Do Not Match", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // no username in the system with that name
                                        Toast.makeText(getApplicationContext(), "Could Not Find Account", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }

            }
        });
    }
}