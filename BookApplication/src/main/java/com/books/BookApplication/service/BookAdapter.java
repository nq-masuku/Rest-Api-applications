package com.books.BookApplication.service;

import com.books.BookApplication.domain.Author;
import com.books.BookApplication.domain.Book;
import com.books.BookApplication.domain.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter {

    public static BookCopyDTO getBookCopyDTOFromBookCopy(BookCopy book,Book b){
        BookCopyDTO bookDto=new BookCopyDTO(book.getScanCode(),book.isAvailable());
        b.setCopies(null);
        bookDto.setBookDTO(b);
        return bookDto;
    }

    public static BookDTO getBookDTOFromBook(Book book){
        BookDTO bookDto=new BookDTO(book.getIsbn(), book.getTitle(), book.getAuthors());
        List<BookCopyDTO> dtoBookCopies = new ArrayList<>();

        //transforming BookCopy to BookCopyDTO
        for(BookCopy copy: book.getCopies()){
            BookCopyDTO copyDTO=new BookCopyDTO(copy.getScanCode(), copy.isAvailable());
            copyDTO.setBookDTO(book);
            dtoBookCopies.add(copyDTO);
        }
        bookDto.setCopies(dtoBookCopies);
        return bookDto;
    }

    public static BookDTO getOnlyBookDTOFromBook(Book book){
        BookDTO bookDto=new BookDTO(book.getIsbn(), book.getTitle(), book.getAuthors());
        bookDto.setCopies(null);
        return bookDto;
    }

    public static Book getBookFromBookDTO(BookDTO bookDTO){
        Book book=new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getAuthors());
        List<BookCopy> bookCopies = new ArrayList<>();

        //transforming BookCopyDTO BookCopy
        if(bookDTO.getCopies() !=null ){
            for(BookCopyDTO copyDTO: bookDTO.getCopies()){
                BookCopy copy=new BookCopy(copyDTO.getScanCode(), copyDTO.isAvailable());
                bookCopies.add(copy);
            }
        }
        book.setCopies(bookCopies);
        return book;
    }

    public static List<BookDTO> getBookDTOList(List<Book> books){
        List<BookDTO> dtoBookList=new ArrayList<>();
        for(Book book: books){
            dtoBookList.add(getBookDTOFromBook(book));
        }
        return dtoBookList;
    }

    public static List<BookDTO> getOnlyBookDTOList(List<Book> books){
        List<BookDTO> dtoBookList=new ArrayList<>();
        for(Book book: books){
            dtoBookList.add(getOnlyBookDTOFromBook(book));
        }
        return dtoBookList;
    }
}
