package com.library.LibraryApplication.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDTO {
	private List<BookCopyDTO> copies;
	private List<Author> authors;
	private String isbn;
	private String title;

	public BookDTO(){}

	public BookDTO(String isbn, String title, List<Author> authors) {
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		copies=new ArrayList<>();
	}

	public BookDTO(String isbn, String title, List<Author> authors, String scanCode) {
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		copies=new ArrayList<>();
		copies.add(new BookCopyDTO(scanCode, true));
	}

	public List<BookCopyDTO> getCopies() {
		return copies;
	}

	public void setCopies(List<BookCopyDTO> copies) {
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

	@Override
	public String toString() {
		return "Book{" +
				"isbn='" + isbn + '\'' +
				", title='" + title + '\'' +
				", authors=" + authors +
				'}';
	}
}
