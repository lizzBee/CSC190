package com.csc190.bookbazaar;

import java.net.URL;

//need a way to access hofID from within book without asking user for it
//store book inside of a user to access the ID?
//how to show book on home screen? store book in an array?

public class Book {
    String title, ISBN, price, condition, picture, hofstraID;


    public Book(String bookTitle, String ISBNnum, String cond, String prix, String pictureURL, String hofID) {
        title = bookTitle;
        ISBN = ISBNnum;
        condition = cond;
        price = prix;
        picture = pictureURL;
        hofstraID = hofID;
        isStarred = false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setHofstraID(String hofstraID) {
        this.hofstraID = hofstraID;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    private boolean isStarred;

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getPicture() {
        return picture;
    }

    public String getHofstraID() {
        return hofstraID;
    }

    public boolean isStarred() {
        return isStarred;
    }





}
