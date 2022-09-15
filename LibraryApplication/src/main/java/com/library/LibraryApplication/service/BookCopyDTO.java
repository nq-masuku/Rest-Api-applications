package com.library.LibraryApplication.service;

public class BookCopyDTO {
	private BookDTO bookDTO;
	private String scanCode;
	private boolean isAvailable;
	BookCopyDTO(String scanCode, boolean isAvailable) {
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

	@Override
	public String toString() {
		return "BookCopyDTO{" +
				"book=" + bookDTO +
				", scanCode='" + scanCode + '\'' +
				", isAvailable=" + isAvailable() +
				'}';
	}
}
