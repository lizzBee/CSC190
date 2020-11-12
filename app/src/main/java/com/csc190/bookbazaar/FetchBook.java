package com.csc190.bookbazaar;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * AsyncTask implementation that opens a network connection and
 * query's the Book Service API.
 */
public class FetchBook extends AsyncTask<String,Void,String>{

    // Variables for the search input field, and results TextViews
    private final String Condition;
    private final String Price;
    FirebaseFirestore fStore;
    DatabaseReference myRef;
    DocumentReference bookRef;
    public String query = "";
    // Class name for Log tag
   // private static final String LOG_TAG = FetchBook.class.getSimpleName();
    public String bookID;


    // Constructor providing a reference to the views in MainActivity
    public FetchBook(String condition, String price) {
        this.Condition = condition;
        this.Price = price;
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
        query = queryString;

        // Set up variables for the try block that need to be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        // Attempt to query the Books API.
        try {
            // Base URI for the Books API.
            final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?q=isbn:"  +queryString; //q=isbn:

            /*final String QUERY_PARAM = "q"; // Parameter for the search string.
            final String MAX_RESULTS = "maxResults"; // Parameter that limits search results.
            final String PRINT_TYPE = "printType"; // Parameter to filter by print type.*/

            // Build up your query URI, limiting results to 10 items and printed books.
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    // .appendQueryParameter(QUERY_PARAM, queryString)
                    /*.appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")*/
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
     * @param bookISBN Result from the doInBackground method containing the raw JSON response,
     *          or null if it failed.
     */
    @Override
    protected void onPostExecute(final String bookISBN) {
        super.onPostExecute(bookISBN);
        fStore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("BookID/id");
        final String userID = mAuth.getCurrentUser().getUid();
       // final DocumentReference userRef = fStore.collection("users").document(userID);
        
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookID = snapshot.getValue().toString();
                int idNum = Integer.parseInt(bookID)+1;
                myRef.setValue(String.valueOf(idNum));
                //myRef.setValue("1");
                bookRef = fStore.collection("books").document(bookID);
                Log.w(TAG, bookID);
                try {
                    // bookRef = fStore.collection("books").document(bookID);
                    // Convert the response into a JSON object.
                    JSONObject jsonObject = new JSONObject(bookISBN);
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
                            //isbn = volumeInfo.getString("ISBN");
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        // Move to the next item.
                        i++;
                    }

                    // If both are found, display the result.
                    if (title != null && authors != null){
                       // userRef.update("Listing", FieldValue.arrayUnion(bookID)); //dont need this
                        Map<String, Object> book = new HashMap<>();
                        book.put("Title", title); //pull these two fields from isbn?
                        book.put("Author",authors);
                        book.put("ISBN", query);
                        book.put("Condition", Condition);
                        book.put("Price", Price);
                        book.put("Owner", userID);
                        book.put("Image", "http://covers.openlibrary.org/b/isbn/" + query +"-M.jpg");
                        book.put("ID", bookID);
                        bookRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "book has been created for isbn:" + bookISBN);

                            }});
                        //Toast.makeText(AddBook.this, "WE GOT THIS FAR", Toast.LENGTH_LONG)
                    } else {
                        Map<String, Object> book = new HashMap<>();
                        book.put("Title", title); //pull these two fields from isbn?
                        book.put("Author",authors);
                        book.put("ISBN", query);
                        book.put("Condition", Condition);
                        book.put("Price", Price);
                        book.put("Image", "http://covers.openlibrary.org/b/isbn/" + query +"-M.jpg");
                        book.put("ID", bookID);
                        bookRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "book has been created for isbn:" + bookISBN);

                            }});
                    }

                } catch (Exception e){
                    // If onPostExecute does not receive a proper JSON string,
                    // update the UI to show failed results.
                    //mTitleText.setText("no_results");
                    //mAuthorText.setText("");
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}
