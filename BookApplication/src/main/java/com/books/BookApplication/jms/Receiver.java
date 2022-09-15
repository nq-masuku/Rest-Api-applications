package com.books.BookApplication.jms;

import com.books.BookApplication.domain.Author;
import com.books.BookApplication.service.BookCopyDTO;
import com.books.BookApplication.service.BookDTO;
import com.books.BookApplication.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Receiver {

    @Autowired
    private BookService service;

    @JmsListener(destination = "BookApplicationQueue")
    public void message(String msg){
        ObjectMapper mapper=new ObjectMapper();
        try{
            WrapperObject book=mapper.readValue(msg, WrapperObject.class);
            BookDTO dto=wrapper(book);
            if(book.operation.equals("add")){
                service.addBook(dto);
            }else{
                service.updateBook(dto);
            }
        }catch(Exception e){
            e.fillInStackTrace();
        }
    }

    public BookDTO wrapper(WrapperObject obj){
        List<Author> authors=new ArrayList<>();
        List<BookCopyDTO> copies=new ArrayList<>();
        String[] names= obj.firstName.split(",");
        String[] surnames= obj.lastName.split(",");
        for(int i=0; i<names.length; i++){
            authors.add(new Author(names[i], surnames[i]));
        }
        BookDTO book=new BookDTO(obj.isbn, obj.title, authors);
        BookCopyDTO copy=new BookCopyDTO(obj.scanCode, obj.isAvailable);
        copies.add(copy);
        book.setCopies(copies);
        System.out.println(book);
        return book;
    }
}
