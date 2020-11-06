/*package com.csc190.bookbazaar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reg2 extends Activity {


    private static String TAG;
    private FirebaseAuth mAuth;
    Button myButton;
    EditText fName, lName, email, hofID, user, pass; //need fields for fName and lName on registration.xml

    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.registration);

        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //  updateUI(currentUser); //send to home screen if already logged in
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        hofID = (EditText) findViewById(R.id.editTextid);
        //user = (EditText)findViewById(R.id.editTextTextPersonName2);
        pass = (EditText) findViewById(R.id.editTextTextPassword2);

        myButton = (Button) findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            database = FirebaseDatabase.getInstance();
                                            myRef = database.getReference("User");

                                            //get values
                                            String sEmail = email.getEditableText().toString();
                                            TAG = hofID.getText().toString();
                                            String sPass = pass.getText().toString();

//                                            //User help = new User(sEmail, sID, test, sPass, "Hey");

                                            //myRef.child(sID).setValue(help);
                                            setContentView(R.layout.login);
                                        }
            });
    }



  //  public void onStart() {
  //      super.onStart();
   /*     // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  updateUI(currentUser); //send to home screen if already logged in
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        hofID = (EditText) findViewById(R.id.editTextid);
        //user = (EditText)findViewById(R.id.editTextTextPersonName2);
        pass = (EditText) findViewById(R.id.editTextTextPassword2);

        myButton = (Button) findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");

                //get values
                String sEmail = email.getEditableText().toString();
                TAG = hofID.getText().toString();
                String sPass = pass.getText().toString();

                createAccount(sEmail, sPass);
                //User help = new User(sEmail, sID, test, sPass, "Hey");

                //myRef.child(sID).setValue(help);
                setContentView(R.layout.login);
            }
        });



    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
}


*/
