package com.csc190.bookbazaar;

//need a way to access hofID from within book without asking user for it
//store book inside of a user to access the ID?
//how to show book on home screen? store book in an array?

public class Book {
    String title, ISBN, price, condition, hofstraID;
    String picture;
    String api = "AIzaSyCVDsAsNnZWvztoozZMSObD4WxPvnZ1L7E";
    private boolean isStarred;

    //OpenBooks for images: http://covers.openlibrary.org/b/isbn/" + book.ISBN +"-M.jpg"

    public Book(String bookTitle, String ISBNnum, String cond, String prix, String pix, String hofID) {
        title = bookTitle;
        ISBN = ISBNnum;
        condition = cond;
        price = prix;
        picture = pix;
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

    public void setPicture(String pic) {this.picture = pic;}

    public String getPicture() {return picture;}
    public void setHofstraID(String hofstraID) {
        this.hofstraID = hofstraID;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

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

    public String getHofstraID() {
        return hofstraID;
    }

    public boolean isStarred() {
        return isStarred;
    }





}
