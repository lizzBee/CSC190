package com.csc190.bookbazaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends Activity {
    EditText searchBar;
    ImageButton searchButton;
    String searchText;
    //FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        searchBar= findViewById(R.id.searchbar);
        searchButton = findViewById(R.id.searchButton);


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
}
