package com.csc190.bookbazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


public class my_listing extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    //CollectionReference docRef;
    DocumentReference docRef;
    CollectionReference bookRef;
    RecyclerView recyclerview;
    FirestoreRecyclerAdapter adapter;
    private static final String TAG =my_listing.class.getSimpleName();
    ImageView sad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_listing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        sad = findViewById(R.id.imageView);
        Query query = fStore.collection("books").whereEqualTo("Owner", mAuth.getUid());
        //DocumentReference docRef = fStore.collection("books").whereEqualTo("Owner", mAuth.getUid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot document = task.getResult();
                if (document.isEmpty()) {
                  //  Log.d(TAG, "SHE'S EMPTY!!!!");
                    sad.setVisibility(View.VISIBLE);
                }
                else {sad.setVisibility(View.INVISIBLE);}
            }
        });

        recyclerview = findViewById(R.id.booklist_ml);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Book, bookViewHolder>(options) {

            @NonNull
            @Override
            public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
                sad.setVisibility(View.INVISIBLE);
                /*if (recyclerview.getItemDecorationCount() == 0) {Log.d(TAG, "SHE'S EMPTY!!!!");}
                else {Log.d(TAG, "SHE'S having issues!!!!");}*/
                return new bookViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull bookViewHolder holder, int position, @NonNull final Book model) {
                Log.w(TAG, model.getTitle() +" "  +model.getPrice()  + " !!!!!!!!!!!");
                holder.title.setText(model.getTitle());
                holder.price.setText(model.getPrice());
                holder.condition.setText(model.getCondition());
                Picasso.with(getApplicationContext()).load(model.getImage()).placeholder(R.drawable.oopsie).error(R.drawable.oopsie).into(holder.image); //still does not load oopsie
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fStore.collection("books").document(model.getID()).delete();
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
                switch (menuItem.getItemId()) {
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
    class bookViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, condition;
        ImageView image;
        Button deleteButton;
        public bookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_bl);
            price = itemView.findViewById(R.id.price_bl);
            condition = itemView.findViewById(R.id.condition_bl);
            image = itemView.findViewById(R.id.imageView3);
            deleteButton = itemView.findViewById(R.id.buttondelete);

        }
    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
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