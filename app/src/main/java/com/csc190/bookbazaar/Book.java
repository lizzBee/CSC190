package com.csc190.bookbazaar;

//need a way to access hofID from within book without asking user for it
//store book inside of a user to access the ID?
//how to show book on home screen? store book in an array?

public class Book {
    private String Title, Author, ISBN, Condition, Price, Owner;
    String api = "AIzaSyCVDsAsNnZWvztoozZMSObD4WxPvnZ1L7E";

    //OpenBooks for images: http://covers.openlibrary.org/b/isbn/" + book.ISBN +"-M.jpg"
    public Book() {
    }
    public Book(String cond, String pix, String prix, String bookTitle) {
        this.Title = bookTitle;
        //ISBN = ISBNnum;
        this.Condition = cond;
        this.Price = prix;
      //  this.Picture = pix;
       // hofstraID = hofID;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

  /*  public void setISBN(String ISBN) {
        this.ISBN = ISBN;
        public String getISBN() {
        return ISBN;
    }}*/


    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getCondition() {
        return Condition;
    }


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
