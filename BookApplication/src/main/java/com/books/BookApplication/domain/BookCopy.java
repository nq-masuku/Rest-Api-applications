package com.books.BookApplication.domain;

public class BookCopy{
	private Book book;
	private String scanCode;
	private boolean isAvailable;
	public BookCopy(String scanCode, boolean isAvailable) {
		this.scanCode = scanCode;
		this.isAvailable = isAvailable;
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		Book book1=new Book(book.getIsbn(), book.getTitle(), book.getAuthors());
		this.book = book1;
	}

	@Override
	public String toString() {
		return "BookCopy{"
			    + book +
				", scanCode='" + scanCode + '\'' +
				", isAvailable=" + isAvailable +
				'}';
	}
}
