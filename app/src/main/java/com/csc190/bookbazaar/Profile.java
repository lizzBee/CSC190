package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile extends Activity {
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");
    FirebaseAuth mAuth;
    FirebaseUser user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User"); // .child(user_key) to get the user logged in
        mAuth = FirebaseAuth.getInstance();
        // 3 texts, 1 button
        final Button editProfile;
        final TextView email;
        email = findViewById(R.id.textView25);
        String em = user.getEmail();
       // String passWord = user.getPass();
      //  String eMail = us.getEmail();
//
       // user.setText(userName);
        email.setText(em);
       // pass.setText(passWord);
        editProfile = findViewById(R.id.button8);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }});
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.tab_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_starred:
                        startActivity(new Intent(getApplicationContext(), Starred.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_yourlistings:
                        startActivity(new Intent(getApplicationContext(), my_listing.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_profile:
                        return true;
                }
                return false;
            }
        });
        /*
        String user_key = "";

        user_key =  "12345";
        // finds the user in the database based on key found when logging in
        // updates profile display to be information from database
        FirebaseDatabase.getInstance().getReference().child("User").child(user_key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User us = dataSnapshot.getValue(User.class);
                        String userName = us.getHofstraID();
                        String passWord = us.getPass();
                        String eMail = us.getEmail();

                        user.setText(userName);
                        email.setText(eMail);
                        pass.setText(passWord);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");
                setContentView(R.layout.edit_profile);
            }
        });
        */

    }
}
