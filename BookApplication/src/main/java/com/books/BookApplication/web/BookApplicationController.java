package com.books.BookApplication.web;

import com.books.BookApplication.domain.BookCopy;
import com.books.BookApplication.service.BookCopyDTO;
import com.books.BookApplication.service.BookDTO;
import com.books.BookApplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class BookApplicationController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) {
        bookService.addBook(book);
        return new ResponseEntity<> (book, HttpStatus.OK);
    }

    @PutMapping("/books")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        BookDTO results = bookService.updateBook(book);
        if(results == null){
            return new ResponseEntity<>(new CustomErrorType("Book was not found for the requested update"), HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<> (book, HttpStatus.OK);
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/books")
    public ResponseEntity<?> searchBooks(
            @RequestParam(value="scancode", required = false) String scancode,
            @RequestParam(value="isbn", required = false) String isbn,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="availablecopies", required = false) String availablecopies,
            @RequestParam(value="title", required = false) String title) {

        CustomErrorType error=new CustomErrorType("Requested book not available");

        if(scancode != null){
            Optional<BookDTO> book = bookService.findByScanCode(scancode);
            return responseEntityForOptional(book,error);
        }
        if(isbn != null){
            Optional<BookDTO> book = bookService.findByIsbn(isbn);
            return responseEntityForOptional(book,error);
        }
        if(author != null){
            Optional<BookDTO> book = bookService.findByAuthor(author);
            return responseEntityForOptional(book,error);
        }
        if(title != null){
            Optional<BookDTO> book = bookService.findByTitle(title);
            return responseEntityForOptional(book,error);
        }
        if(availablecopies != null){
            Books book = bookService.findBookWithAvailableCopies();
            return responseEntityForList(book,error);
        }
        return responseEntityForList(bookService.findAll(), error);

    }

    @GetMapping("/books/copy/{isbn}")
    public ResponseEntity<?> getAvailableCopyByIsbn(@PathVariable String isbn) {
        Optional<BookCopyDTO> copy = bookService.findByIsbnAndAvailable(isbn);
        if(!copy.isPresent()){
            return new ResponseEntity<>(new CustomErrorType("No available copy for this book with Isbn:"+isbn), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<> (copy, HttpStatus.OK);
    }

    @PostMapping("/books/copy")
    public ResponseEntity<?> addBookCopy(
            @RequestParam(value="scancode") String scancode,
            @RequestParam(value="isbn") String isbn) {
        BookCopy copy = bookService.addBookCopy(scancode, isbn);
        BookCopyDTO dto=new BookCopyDTO(scancode, true);
        if(copy ==null){
            return new ResponseEntity<>(new CustomErrorType("Book was not found, this copy cannot be added"), HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<> (dto, HttpStatus.OK);
    }
    public ResponseEntity<?> responseEntityForOptional(Optional<BookDTO> book, CustomErrorType error){
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> responseEntityForList(Books books, CustomErrorType error){
        if(books != null){
            return new ResponseEntity<>(books, HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<?> welcomeMessage(){
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }
}
