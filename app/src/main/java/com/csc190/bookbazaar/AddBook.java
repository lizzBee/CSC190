package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends Activity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// =
    FirebaseFirestore fStore;
    String bookID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        final EditText title, isbn, price, cond;
        Button addButton;
        addButton = findViewById(R.id.button12);
        final String TAG = Registration.class.getSimpleName();

        title = findViewById(R.id.editTextTextPersonName2);//get rid of this?
        isbn = findViewById(R.id.editTextTextPersonName4);
        //cond = findViewById(R.id.editTextTextPersonName2); //dropdown?
        price = findViewById(R.id.editTextTextPersonName6); //price oriented edit text?
        //right now this is accessible from the "edit" button on the my listing interface
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookID = "111";
                //  final String bookTitle = title.getText().toString().trim();
                final String bookISBN = isbn.getText().toString();
                // final String bookCond = user.getText().toString();
                final String bookPrice = price.getText().toString().trim();
                DocumentReference bookRef = fStore.collection("books").document(bookID);
                DocumentReference userRef = fStore.collection("users").document(bookID);
                Map<String, Object> book = new HashMap<>();
                book.put("Title", "Brave New World"); //pull these two fields from isbn?
                book.put("Author","Aldous Huxley");
                book.put("ISBN", bookISBN);
                book.put("Price", bookPrice);
                //add book to user list?
                bookRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d(TAG, "book has been created for isbn:" + bookISBN);
                         startActivity(new Intent(getApplicationContext(), my_listing.class));
                     }});

            }

        });

/*
        //create button and textfields
        Button addbookButton;
        final EditText title, ISBN, condition, price;




        //get values
        title = findViewById(R.id.editTextTextPersonName3);
        ISBN = findViewById(R.id.editTextTextPersonName4);
        condition = findViewById(R.id.editTextTextPersonName5);
        price = findViewById(R.id.editTextTextPersonName6);

        addbookButton = findViewById(R.id.button12);

        addbookButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference("Book");
                        String picURL;
                      //  User user = null;

                        //get values
                        String sTitle = title.getEditableText().toString();
                        String sISBN = ISBN.getEditableText().toString();
                        String sCondition = condition.getEditableText().toString();
                        String sPrice = price.getEditableText().toString();
                        picURL =  "https://covers.openlibrary.org/b/isbn/" + sISBN +"-M.jpg";
      //Commented out code is for reading images to view with URL from Firebase
        /*                URL url = null;
                        try {
                            url = new URL("https://covers.openlibrary.org/b/isbn/" + sISBN +"-M.jpg");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Bitmap bmp = null; //this is probably the error
                        try {
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(bmp);
/
                        if (sTitle.equals("") || sISBN.equals("") || sCondition.equals("") || sPrice
                                .equals("")) {
                            Toast.makeText(getApplicationContext(),"Some fields are empty!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Book book = new Book(sTitle, sISBN, sCondition, sPrice, picURL, ""); //or image?
                            myRef.child(sISBN).setValue(book); //should be changed to hofID?
                            Toast.makeText(getApplicationContext(), "Book added successfully!", Toast.LENGTH_LONG).show();
                            setContentView(R.layout.home);
                        }
                    }
                }
        );
    }*/
    }
}
