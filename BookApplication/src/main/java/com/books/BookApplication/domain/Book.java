package com.books.BookApplication.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Book{
	private List<BookCopy> copies;
	private List<Author> authors;
	@Id
	private String isbn;
	private String title;

	public Book(){}
	public Book(String isbn, String title, List<Author> authors, String scanCode) {
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		copies=new ArrayList<>();
		copies.add(new BookCopy(scanCode, true));
	}

	public Book(String isbn, String title, List<Author> authors) {
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
	}

	public void addCopy(String scanCode) {
          copies.add(new BookCopy(scanCode, true));
	}

	public void updateCheckedOutCopy(String scanCode) {
		for(int i=0; i< copies.size(); i++){
			if(copies.get(i).getScanCode().equals(scanCode)) {
				copies.get(i).changeAvailability();
			}
		}
	}

	public void returnBookCopy(String scanCode){
		for(int i=0; i< copies.size(); i++){
			if(copies.get(i).getScanCode().equals(scanCode)) {
				copies.get(i).changeAvailability();
			}
		}
	}

	public List<BookCopy> getCopies() {
		return copies;
	}

	public void setCopies(List<BookCopy> copies) {
		this.copies = copies;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Book{" +
				"isbn='" + isbn + '\'' +
				", title='" + title + '\'' +
				", authors=" + authors +
				'}';
	}
}
