package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBook extends Activity {

    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// =
    FirebaseFirestore fStore;
    String bookID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

         final Spinner dropdown = findViewById(R.id.cond_spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Utter Disarray", "Poor", "Good", "Like New"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

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
            public void onClick(View v){
                //  final String bookTitle = title.getText().toString().trim();
                final String bookISBN = isbn.getText().toString();
                final String bookCond = dropdown.getSelectedItem().toString();
                final String bookPrice = price.getText().toString().trim();

               // DocumentReference userRef = fStore.collection("users").document(bookID);
                new FetchBook(bookCond, bookPrice).execute(bookISBN);
                startActivity(new Intent(getApplicationContext(), my_listing.class));
              /*  Map<String, Object> book = new HashMap<>();
                book.put("Title", "Brave New World"); //pull these two fields from isbn?
                book.put("Author","Aldous Huxley");
                book.put("ISBN", bookISBN);
                book.put("Condition", bookCond);
                book.put("Price", bookPrice);
                //add book to user list?
                bookRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d(TAG, "book has been created for isbn:" + bookISBN);

                     }});*/

            }

        });



/**
 * AsyncTask implementation that opens a network connection and
 * query's the Book Service API.
 */
class FetchBook extends AsyncTask<String,Void,String>{

    // Variables for the search input field, and results TextViews
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    // Class name for Log tag
    private final String LOG_TAG = FetchBook.class.getSimpleName();

    // Constructor providing a reference to the views in MainActivity
    public FetchBook(TextView titleText, TextView authorText, EditText bookInput) {
        this.mTitleText = titleText;
        this.mAuthorText = authorText;
        this.mBookInput = bookInput;
    }


    /**
     * Makes the Books API call off of the UI thread.
     *
     * @param params String array containing the search data.
     * @return Returns the JSON string from the Books API or
     *         null if the connection failed.
     */
    @Override
    protected String doInBackground(String... params) {

        // Get the search string
        String queryString = params[0];


        // Set up variables for the try block that need to be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        // Attempt to query the Books API.
        try {
            // Base URI for the Books API.
            final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";

            final String QUERY_PARAM = "q"; // Parameter for the search string.
            final String MAX_RESULTS = "maxResults"; // Parameter that limits search results.
            final String PRINT_TYPE = "printType"; // Parameter to filter by print type.

            // Build up your query URI, limiting results to 10 items and printed books.
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            // Open the network connection.
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Read the response string into a StringBuilder.
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                // Stream was empty.  No point in parsing.
                // return null;
                return null;
            }
            bookJSONString = builder.toString();

            // Catch errors.
        } catch (IOException e) {
            e.printStackTrace();

            // Close the connections.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        // Return the raw response.
        return bookJSONString;
    }

    /**
     * Handles the results on the UI thread. Gets the information from
     * the JSON and updates the Views.
     *
     * @param s Result from the doInBackground method containing the raw JSON response,
     *          or null if it failed.
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }

            // If both are found, display the result.
            if (title != null && authors != null){
                mTitleText.setText(title);
                mAuthorText.setText(authors);
                mBookInput.setText("");
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText("no_results");
                mAuthorText.setText("");
            }

        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText("no_results");
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }
}










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
