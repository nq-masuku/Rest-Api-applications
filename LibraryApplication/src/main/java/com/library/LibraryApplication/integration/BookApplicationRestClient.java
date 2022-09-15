package com.library.LibraryApplication.integration;

import com.library.LibraryApplication.jms.IJMSSender;
import com.library.LibraryApplication.jms.WrapperObject;
import com.library.LibraryApplication.service.Author;
import com.library.LibraryApplication.service.BookCopyDTO;
import com.library.LibraryApplication.service.BookDTO;
import com.library.LibraryApplication.service.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BookApplicationRestClient {
    RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private IJMSSender ijmsSender;

    Logger logger= LoggerFactory.getLogger(BookApplicationRestClient.class);

    @Value("${server.url}")
    private String serverUrl;

    public BookDTO addBook(BookDTO book) {
        try{
            ijmsSender.sendJMSMessage(wrapper(book, "add"));
        }catch(Exception e){
            book=null;
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return book;
    }

    public BookDTO updateBook(BookDTO book) {
        try{
            ijmsSender.sendJMSMessage(wrapper(book, "update"));
        }catch(Exception e){
            book=null;
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return book;
    }

    public void deleteBook(String isbn) {
        try{
            restTemplate.delete(serverUrl+"/"+isbn);
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
    }
    public BookDTO searchBooks(String searchValue, String valueName){
        BookDTO book=null;
        try{
            if(valueName.equals("scancode")){
                book =restTemplate.getForObject(serverUrl+"?scancode={scancode}",BookDTO.class,searchValue);
            }
            if(valueName.equals("isbn")){
                book =restTemplate.getForObject(serverUrl+"?isbn={isbn}",BookDTO.class,searchValue);
            }
            if(valueName.equals("author")){
                book =restTemplate.getForObject(serverUrl+"?author={author}",BookDTO.class,searchValue);
            }

            if(valueName.equals("title")){
                book =restTemplate.getForObject(serverUrl+"?title={title}",BookDTO.class,searchValue);
            }
        }catch(Exception e){
           logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return book;
    }

    public Books findBooksWithAvailableCopies(){
        Books books=null;
        try{
            books=restTemplate.getForObject(serverUrl+"?availablecopies={availablecopies}",Books.class,"yes");
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return books;
    }

    public BookCopyDTO getAvailableCopyByIsbn(String isbn) {
        BookCopyDTO book=null;
        try{
            book =restTemplate.getForObject(serverUrl+"/copy/"+isbn, BookCopyDTO.class);
            if(!book.isAvailable()){book.changeAvailability();}
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return book;
    }

    public void addBookCopy(String scancode,String isbn) {
        try{
            restTemplate.postForLocation(serverUrl+"/copy?isbn={isbn}&scancode={scancode}",BookCopyDTO.class, isbn, scancode);
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }

    }

    public Books getAllBooks(){
        Books response=null;
        try{
            response = restTemplate.getForObject(serverUrl, Books.class);
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return response;
    }

    public WrapperObject wrapper(BookDTO book, String operation){
        String firstName="";
        String lastName="";
        int c=0;
        if(book.getAuthors() !=null){
            for(Author au:book.getAuthors()){
                if(c==0){
                    firstName+=au.getFirstName();
                    lastName+=au.getLastName();
                }else{
                    firstName+=","+au.getFirstName();
                    lastName+=","+au.getLastName();
                }
                c++;
            }
        }
        WrapperObject obj=new WrapperObject(book.getIsbn(),book.getTitle(),firstName, lastName,book.getCopies().get(0).getScanCode(),
                book.getCopies().get(0).isAvailable(), operation);
       return obj;
    }
}
