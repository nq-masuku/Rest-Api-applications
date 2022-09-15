package com.books.BookApplication.web;


import com.books.BookApplication.service.BookDTO;

import java.util.ArrayList;
import java.util.Collection;

public class Books {
    private Collection<BookDTO> books = new ArrayList<BookDTO>();

    public Collection<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookDTO> books) {
        this.books = books;
    }
}
