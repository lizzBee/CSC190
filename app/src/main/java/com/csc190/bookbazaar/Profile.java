package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile extends Activity {
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    DocumentReference docRef;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User"); // .child(user_key) to get the user logged in
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        docRef = fStore.collection("users").document(user.getUid());

        // 3 texts, 1 button
        final Button editProfile;
        final TextView email, username, hofid;
        email = findViewById(R.id.textView25);
        username = findViewById(R.id.textView7);
        hofid = findViewById(R.id.textView9);

        String em = user.getEmail();
        email.setText(em);

        editProfile = findViewById(R.id.button8);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("username");
                    username.setText(name);
                    String id = documentSnapshot.getString("HofstraID");
                    hofid.setText(id);
                }
            }
        });

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
