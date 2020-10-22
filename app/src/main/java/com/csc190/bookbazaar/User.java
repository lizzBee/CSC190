package com.csc190.bookbazaar;

public class User {
    String fName, lName, hofstraID, email, pass;

    public User() {
        this.fName = fName;
    }

    public User(String fName, String lName, String hofstraID, String email, String pass) {
        this.fName = fName;
        this.lName = lName;
        this.hofstraID = hofstraID;
        this.email = email;
        this.pass = pass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getHofstraID() {
        return hofstraID;
    }

    public void sethofstraID(String hofstraID) {
        this.hofstraID = hofstraID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}