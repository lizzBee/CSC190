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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends Activity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    FirebaseFirestore fStore;
    String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("User");
        final String TAG = Registration.class.getSimpleName();

   // public void onStart (Bundle saveInstanceState) {
    //    super.onStart();

        // Write a message to the database
        Button myButton;
        final EditText BarEmail, hofID, user, pass; //need fields for fName and lName on registration.xml

        BarEmail = findViewById(R.id.editTextTextEmailAddress2);
        hofID = findViewById(R.id.editTextid);
        user = findViewById(R.id.editTextTextPersonName2);
        pass = findViewById(R.id.editTextTextPassword2);

    /*    if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent (getApplicationContext(), Login.class));
            finish();
        }
*/
        myButton = findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = BarEmail.getText().toString().trim();
                final String ID = hofID.getText().toString();
                final String username = user.getText().toString();
                final String password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    BarEmail.setError("Email is Required!");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    pass.setError("Password is Required!");
                    return;
                }
                if(TextUtils.isEmpty(username)) {
                    user.setError("Username is Required!");
                    return;
                }
                if(username.length() > 15) {
                    user.setError("Keep username under 15 characters!");
                    return;
                }
                if(password.length() < 8) {
                    pass.setError("Password must at least 8 characters!");
                    return;
                }
                if(ID.length() != 8 || ID.charAt(0) != 7) {
                    pass.setError("Invalid Id!");
                    return;
                }
                //create user
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Registration.this, "User Created Successfully!", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID); //goes to "user" section of firestore and adds user
                            Map<String,Object> user = new HashMap<>(); //first arg is field, second is information
                            user.put("Email", email);
                            user.put("HofstraID", ID);
                            user.put("username", username);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSurccess: user Profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        else {
                            Toast.makeText(Registration.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
