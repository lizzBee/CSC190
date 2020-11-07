package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends Activity {
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");
    FirebaseAuth mAuth;
    FirebaseUser user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), "WOOOO!!!!", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.edit_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User"); // .child(user_key) to get the user logged in
        mAuth = FirebaseAuth.getInstance();
        final String TAG = Registration.class.getSimpleName();
        final Button editProfile;
        EditText username = findViewById(R.id.editTextTextPassword3);
        EditText newPassword = findViewById(R.id.editTextTextEmailAddress);
        final String email = username.getText().toString();
        final String password = newPassword.getText().toString();

        editProfile = findViewById(R.id.button6);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //code to edit password->https://github.com/bikashthapa01/firebase-authentication-android/blob/master/app/src/main/java/net/smallacademy/authenticatorapp/EditProfile.java
                                Log.d(TAG, "User password updated.");//8 chars
                            }
                        }
                    });
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });



}}
