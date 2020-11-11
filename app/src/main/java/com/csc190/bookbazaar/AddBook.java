package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddBook extends Activity {

    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    FirebaseFirestore fStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

         final Spinner dropdown = findViewById(R.id.cond_spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Utter Disarray", "Poor", "Good", "Like New"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        final EditText isbn, price;
        Button addButton;
        addButton = findViewById(R.id.button12);

        isbn = findViewById(R.id.editTextTextPersonName4);
        price = findViewById(R.id.editTextTextPersonName6); //price oriented edit text?
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //  final String bookTitle = title.getText().toString().trim();
                final String bookISBN = isbn.getText().toString();
                final String bookCond = dropdown.getSelectedItem().toString();
                final String bookPrice = price.getText().toString().trim();

                new FetchBook(bookCond, bookPrice).execute(bookISBN);
                startActivity(new Intent(getApplicationContext(), my_listing.class));
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
