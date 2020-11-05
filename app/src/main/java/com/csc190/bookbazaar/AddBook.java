package com.csc190.bookbazaar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBook extends Activity {
/*
    FirebaseDatabase database;
    DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

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
