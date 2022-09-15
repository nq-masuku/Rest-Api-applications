package com.books.BookApplication.service;

import com.books.BookApplication.domain.Book;

public class BookCopyDTO {
	private BookDTO bookDTO;
	private String scanCode;
	private boolean isAvailable;
	public BookCopyDTO(String scanCode, boolean isAvailable) {
		this.scanCode = scanCode;
		this.isAvailable = isAvailable;
	}

	public BookCopyDTO() {
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public boolean isAvailable() {
		return isAvailable;
	}
	
	public void changeAvailability() {
		isAvailable = !isAvailable;
	}

	public BookDTO getBookDTO() {
		return bookDTO;
	}

	public void setBookDTO(Book book) {
		BookDTO book1=new BookDTO(book.getIsbn(), book.getTitle(), book.getAuthors());
		this.bookDTO = book1;
	}

	@Override
	public String toString() {
		return "BookCopyDTO{" +
				"book=" + bookDTO +
				", scanCode='" + scanCode + '\'' +
				", isAvailable=" + isAvailable() +
				'}';
	}
}
