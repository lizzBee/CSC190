package com.csc190.bookbazaar;

public class Book {
    String author, title, hofstraID, subject, bookURL;
    int ISBN, condition, price;
    private boolean isStarred;

    public Book(String writer, String bookTitle, String hofID, String sub, String URL, int ISBNnum, int cond, int prix, boolean star) {
        author = writer;
        title = bookTitle;
        hofstraID = hofID;
        subject = sub;
        bookURL = URL;
        ISBN = ISBNnum;
        condition = cond;
        price = prix;
        isStarred = star;
    }





}
