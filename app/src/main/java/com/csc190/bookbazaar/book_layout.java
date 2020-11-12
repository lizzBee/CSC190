package com.csc190.bookbazaar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class book_layout extends FirestoreRecyclerAdapter<Book, book_layout.bookViewHolder>
{
    public book_layout(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull bookViewHolder holder, int i, @NonNull Book listing) {
        holder.title.setText(listing.getTitle());
        holder.price.setText(listing.getPrice());
        holder.condition.setText(listing.getCondition());
        //info is snatched from Book class, and set
    }


    @NonNull
    @Override
    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
        return new book_layout.bookViewHolder(view);
    }


    class bookViewHolder extends RecyclerView.ViewHolder {
        final TextView title, price, condition;
        public bookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_bl);
            price = itemView.findViewById(R.id.price_bl);
            condition = itemView.findViewById(R.id.condition_bl);
        }
    }
}