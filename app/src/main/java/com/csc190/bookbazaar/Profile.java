package com.csc190.bookbazaar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends Activity {
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User"); // .child(user_key) to get the user logged in

        // 3 texts, 1 button
        final Button editProfile;
        final TextView user, email, pass;

        editProfile = (Button)findViewById(R.id.button8);

        user = (TextView)findViewById(R.id.textView7);
        email = (TextView)findViewById(R.id.textView25);
        pass = (TextView)findViewById(R.id.textView9);

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
