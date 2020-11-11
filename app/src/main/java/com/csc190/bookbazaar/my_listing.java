package com.csc190.bookbazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class my_listing extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    //CollectionReference docRef;
    DocumentReference docRef;
    CollectionReference bookRef;
    RecyclerView recyclerview;
    private static final String TAG =my_listing.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_listing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //docRef = fStore.collection("users").document(user.getUid()); //.collection("Listing");
        bookRef = fStore.collection("books"); //.collection("Listing");
      //  Query query = bookRef.where("Owner", "==", mAuth.getUid());
        // need to make a query to fetch results for recycleview ^this doesnt work


        recyclerview =  findViewById(R.id.booklist_ml);
        recyclerview.setLayoutManager(
                new LinearLayoutManager(this));
/*
        FirebaseRecyclerOptions<Book> options
                = new FirebaseRecyclerOptions.Builder<Book>()
                .setQuery(???, book.class)
                .build();*/
        Button addButton;
        addButton = findViewById(R.id.button8);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddBook.class));
             }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab_yourlistings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_starred:
                        startActivity(new Intent(getApplicationContext(), Starred.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_yourlistings:
                        return true;
                    case R.id.tab_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}

/* this is useless now but it may be useful for search
docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@ NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                   DocumentSnapshot documentSnapshot = task.getResult();
                    ArrayList<String> listings = (ArrayList<String>) documentSnapshot.get("Listing");
                    Log.d(TAG, listings.toString());


                    for (String l : listings) {
                        bookRef = fStore.collection("books").document(l);

                        bookRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override //put this info in BOOK
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String title,price,cond;
                                DocumentSnapshot bookSnapshot = task.getResult();
                                title  = bookSnapshot.getString("Title");
                               // title.setText(snaptitle);
                                price = bookSnapshot.getString("Price");
                               // price.setText(snapprice);
                                cond = bookSnapshot.getString("Condition");
                              //  condition.setText(snapcond);
                                Book listBook = new Book(title,price,cond);
                              //  book_layout(listBook);
                            }
                        });
                    }
                }
            }
        });*/