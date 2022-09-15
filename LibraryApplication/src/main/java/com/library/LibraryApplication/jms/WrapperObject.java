package com.library.LibraryApplication.jms;

public class WrapperObject {
    public String isbn;
    public String title;
    public String firstName;
    public String lastName;
    public String scanCode;
    public boolean isAvailable;

    public String operation;

    public WrapperObject(String isbn, String title, String firstName, String lastName, String scanCode, boolean isAvailable, String operation) {
        this.isbn = isbn;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.scanCode = scanCode;
        this.isAvailable = isAvailable;
        this.operation = operation;
    }

    public WrapperObject() {
    }
}
