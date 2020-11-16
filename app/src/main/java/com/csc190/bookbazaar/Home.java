package com.csc190.bookbazaar;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.List;
public class Home extends AppCompatActivity {
    EditText searchBar;
    ImageButton searchButton;
    FirebaseUser user;
    String searchText;
    FirebaseDatabase database;// = FirebaseDatabase.getInstance();
    DatabaseReference myRef;// = database.getReference("user");
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    RecyclerView recyclerview;
    FirestoreRecyclerAdapter adapter;
    DocumentReference docRef;
    TextView greeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User"); // .child(user_key) to get the user logged in
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        docRef = fStore.collection("users").document(user.getUid());
        searchBar= findViewById(R.id.searchbar);
        searchButton = findViewById(R.id.searchButton);
        greeting = findViewById(R.id.textView29);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("username");
                    greeting.setText("Welcome " +name +"!");
                    }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchBar.getText().toString();
                Intent intent = new Intent(Home.this, SearchResults.class);
                intent.putExtra("search", searchText);
                if(!searchText.equals(""))
                    startActivity(intent);
            }
        });


        fStore = FirebaseFirestore.getInstance();
        //query conditions do not work
        Query query = fStore.collection("books").limit(10);//whereArrayContains("Starred", user.getUid());
        recyclerview = findViewById(R.id.recyclerView);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Book, Home.bookViewHolder>(options) {


            @NonNull
            @Override
            public Home.bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_book_layout, parent, false);
                return new Home.bookViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull final Home.bookViewHolder holder, int position, @NonNull final Book model) {
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
                final DocumentReference bookRef = fStore.collection("books").document(model.getID());
                bookRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        final List<String> starred = (List<String>) document.get("Starred");
                        if (starred.contains(user.getUid())) {
                            holder.starredButton.setImageResource(R.drawable.btn_on);
                        }
                holder.starredButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (starred.contains(user.getUid())){
                            holder.starredButton.setImageResource(R.drawable.btn_off);
                            starred.remove(user.getUid());
                        } else {
                            holder.starredButton.setImageResource(R.drawable.btn_on);
                            starred.add(user.getUid());
                        }
                        bookRef.update("Starred", starred);
                    }
                            // if not starred already
                            // set icon to yellow
                            // add to array
                            // else
                            // set icon to grey
                            // delete from array
                        });
                    }});
                Picasso.with(getApplicationContext()).load(model.getImage()).into(holder.image);
              /*  holder.starredButton.setOnClickListener(new View.OnClickListener() { //button doesn't remove anything
                    @Override
                    public void onClick(View v) {
                        model.getStarred().remove(user.getUid()); //since we are in starred interface, the book will already be starred. this button will unstar
                    }
                });*/
            }
        };

//        adapter = new book_layout(options);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.div));
        recyclerview.addItemDecoration(dividerItemDecoration);
        recyclerview.setAdapter(adapter);

        /////////////////////////////////////////////////////////////////////////
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.tab_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_home:
                       return true;
                    case R.id.tab_starred:
                        startActivity(new Intent(getApplicationContext(), Starred.class));
                        overridePendingTransition(0, 0);
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
    static class bookViewHolder extends RecyclerView.ViewHolder {
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
