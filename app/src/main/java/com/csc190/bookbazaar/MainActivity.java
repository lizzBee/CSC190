package com.csc190.bookbazaar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.csc190.bookbazaar.R.layout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.registration);
    }
}