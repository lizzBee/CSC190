package com.csc190.bookbazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class Starred extends AppCompatActivity {
    EditText searchBar;
    ImageButton searchButton;
    String searchText;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    DocumentReference docRef;
    CollectionReference bookRef;
    RecyclerView recyclerview;
    FirestoreRecyclerAdapter adapter;
    private static final String TAG =my_listing.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starred);

        searchBar= findViewById(R.id.starSearchBar);
        searchButton = findViewById(R.id.starSearchButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchBar.getText().toString();
                Intent intent = new Intent(Starred.this, SearchResults.class);
                intent.putExtra("search", searchText);
                if(!searchText.equals(""))
                    startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    //query conditions do not work
        Query query = fStore.collection("books");//whereArrayContains("Starred", user.getUid());
        recyclerview = findViewById(R.id.starRecycle);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Book, Starred.bookViewHolder>(options) {


            @NonNull
            @Override
            public Starred.bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_book_layout, parent, false);
                return new Starred.bookViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull final bookViewHolder holder, int position, @NonNull final Book model) {
                Log.w(TAG, model.getTitle() + " " + model.getPrice() + " !!!!!!!!!!!");
                holder.title.setText(model.getTitle());
                holder.price.setText(model.getPrice());
                holder.condition.setText(model.getCondition());
                holder.author.setText(model.getAuthor());
                docRef = fStore.collection("users").document(model.getOwner());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            String email = documentSnapshot.getString("Email");
                            holder.seller.setText(email);
                        }
                    }
                });
                Picasso.with(getApplicationContext()).load(model.getImage()).into(holder.image);
                holder.starredButton.setOnClickListener(new View.OnClickListener() { //button doesn't remove anything
                    @Override
                    public void onClick(View v) {
                        model.getStarred().remove(user.getUid()); //since we are in starred interface, the book will already be starred. this button will unstar
                    }
                });
            }
        };

//        adapter = new book_layout(options);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.div));
        recyclerview.addItemDecoration(dividerItemDecoration);
        recyclerview.setAdapter(adapter);

        ////////////////////////////////////////////////////////////////////////////////////////

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.tab_starred);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tab_starred:
                        return true;
                    case R.id.tab_yourlistings:
                        startActivity(new Intent(getApplicationContext(), my_listing.class));
                        overridePendingTransition(0, 0);
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
    class bookViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, condition, seller, author;
        ImageView image;
        ImageButton starredButton;
        public bookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_bl);
            author = itemView.findViewById(R.id.author_sbl);
            price = itemView.findViewById(R.id.price_sbl);
            condition = itemView.findViewById(R.id.condition_bl);
            image = itemView.findViewById(R.id.imageView3);
            seller = itemView.findViewById(R.id.seller_sbl);
            starredButton = itemView.findViewById(R.id.imageButton2);
        }
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}