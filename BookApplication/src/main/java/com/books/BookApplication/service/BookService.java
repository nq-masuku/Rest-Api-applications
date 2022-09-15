package com.books.BookApplication.service;

import com.books.BookApplication.domain.Book;
import com.books.BookApplication.domain.BookCopy;
import com.books.BookApplication.repository.BookRepository;
import com.books.BookApplication.web.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void addBook(BookDTO bookDTO){
        Book book=BookAdapter.getBookFromBookDTO(bookDTO);
        bookRepository.save(book);
    }
    public BookDTO updateBook(BookDTO bookDTO){
        Optional<Book> checkBook=bookRepository.findByIsbnIgnoreCase(bookDTO.getIsbn());
        List<BookCopy> newCopies=new ArrayList<>();
        if(checkBook.isPresent()){
            for(BookCopy checked: checkBook.get().getCopies()){
                if(checked.getScanCode().equalsIgnoreCase(bookDTO.getCopies().get(0).getScanCode())){
                    if(checked.isAvailable() != bookDTO.getCopies().get(0).isAvailable()){
                        checked.changeAvailability();
                    }
                }
                newCopies.add(checked);
            }
            Book bk=new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getAuthors());
            bk.setCopies(newCopies);
            bookRepository.save(bk);
            return bookDTO;
        }
        return null;
    }

    public void deleteBook(String isbn){
        Optional<Book> book=bookRepository.findByIsbnIgnoreCase(isbn);
        if(book.isPresent()){
            bookRepository.delete(book.get());
        }
    }

    public Optional<BookDTO> findByIsbn(String isbn){
        Optional<Book> book = bookRepository.findByIsbnIgnoreCase(isbn);
        if(book.isPresent()){
             BookDTO dto=BookAdapter.getBookDTOFromBook(book.get());
             return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<BookDTO> findByTitle(String title){
        Optional<Book> book = bookRepository.findByTitleIgnoreCase(title);
        if(book.isPresent()){
            BookDTO dto=BookAdapter.getBookDTOFromBook(book.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<BookDTO> findByAuthor(String name){
        Optional<Book> book = bookRepository.findBookWithAuthorName(name);
        if(book.isPresent()){
            BookDTO dto=BookAdapter.getBookDTOFromBook(book.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<BookDTO> findByScanCode(String scanCode){
        Optional<Book> book = bookRepository.findBookWithScanCode(scanCode);
        if(book.isPresent()){
            BookDTO dto=BookAdapter.getBookDTOFromBook(book.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Books findBookWithAvailableCopies(){
        List<Book> list = bookRepository.findBooksWithAvailableCopies();
        if(!list.isEmpty()){
            List<BookDTO> books=BookAdapter.getBookDTOList(list);
            Books bookc=new Books();
            bookc.setBooks(books);
            return bookc;
        }
        return null;
    }

    public Optional<BookCopyDTO> findByIsbnAndAvailable(String isbn){
        Optional<Book> book=bookRepository.findByIsbnIgnoreCase(isbn);
        if(book.isPresent()){
            for(BookCopy copy: book.get().getCopies()){
                if(copy.isAvailable()){
                    BookCopyDTO cDto=BookAdapter.getBookCopyDTOFromBookCopy(copy, book.get());
                    cDto.getBookDTO().setCopies(null);
                    return Optional.of(cDto);
                }
            }
        }
        return Optional.empty();
    }

    public Books findAll(){
        List<Book> list = bookRepository.findAll();
        if(!list.isEmpty()) {
            List<BookDTO> books = BookAdapter.getOnlyBookDTOList(list);
            Books bookc = new Books();
            bookc.setBooks(books);
            return bookc;
        }
        return null;
    }

    public BookCopy addBookCopy(String scancode, String isbn){
        Optional<Book> book=bookRepository.findByIsbnIgnoreCase(isbn);
        BookCopy copy=null;
        if(book.isPresent()){
            Book update=book.get();
            copy = new BookCopy(scancode, true);
            update.getCopies().add(copy);
            bookRepository.save(update);
        }
        return copy;
    }
}
