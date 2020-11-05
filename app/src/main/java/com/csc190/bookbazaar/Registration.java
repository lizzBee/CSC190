package com.csc190.bookbazaar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Activity {

    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        // Write a message to the database
        Button myButton;
        final EditText fName, lName, email, hofID, user, pass; //need fields for fName and lName on registration.xml


        email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        hofID = (EditText)findViewById(R.id.editTextid);
        //user = (EditText)findViewById(R.id.editTextTextPersonName2);
        pass = (EditText)findViewById(R.id.editTextTextPassword2);



        myButton = (Button)findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User");


                //get values
                String sEmail = email.getEditableText().toString();
                String sID = hofID.getText().toString();
                String test = email.getText().toString(); //email reference, not user
                String sPass = pass.getText().toString();

                //User help = new User(sEmail, sID, test, sPass, "Hey");

                //myRef.child(sID).setValue(help);
                setContentView(R.layout.login);
                }
        });

    }
}
