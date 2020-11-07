package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends Activity {
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DocumentReference docRef;
    UserInfo profile;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();
        final DocumentReference docRef = fStore.collection("users").document(user.getUid());

        final String TAG = Registration.class.getSimpleName();
        final Button editProfile;
        final EditText username = findViewById(R.id.editTextTextEmailAddress);
        final EditText newPassword = findViewById(R.id.editTextTextPassword3);
        final EditText oldPassword = findViewById(R.id.editTextTextPassword);

        editProfile = findViewById(R.id.button6);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String oldpass = oldPassword.getText().toString();
                final String name = username.getText().toString();
                final String password = newPassword.getText().toString();

                if(TextUtils.isEmpty(oldpass)) {
                    oldPassword.setError("Old password is Required!");
                    return;
                }

                if(!TextUtils.isEmpty(password)) {
                    if(password.length() < 8) {
                        newPassword.setError("Password must at least 8 characters!");
                        return;
                    }
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldpass);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(password);
                            } else {
                                Toast.makeText(getApplicationContext(), "other not success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                if(!TextUtils.isEmpty(name)) {
                    docRef.update("username", name);
                }

                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
}}
