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

public class Registration extends Activity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        mAuth = FirebaseAuth.getInstance();

   // public void onStart (Bundle saveInstanceState) {
    //    super.onStart();


        // Write a message to the database
        Button myButton;
        final EditText BarEmail, hofID, user, pass; //need fields for fName and lName on registration.xml

        BarEmail = findViewById(R.id.editTextTextEmailAddress2);
        hofID = findViewById(R.id.editTextid);
        //user = findViewById(R.id.editTextTextPersonName2);
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
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");


                //get values
                String email = BarEmail.getEditableText().toString();
                String sID = hofID.getText().toString();
                //String test = email.getText().toString(); //email reference, not user
                String password = pass.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    BarEmail.setError("Email is Required!");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    pass.setError("Password is Required!");
                    return;
                }
                if(password.length() < 8) {
                    pass.setError("Password must at least 8 characters!");
                    return;
                }

                //create user
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Registration.this, "User Created Successfully!", Toast.LENGTH_LONG).show();
                            //setContentView(R.layout.login);
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        else {
                            Toast.makeText(Registration.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
