package com.library.LibraryApplication.service;

import com.library.LibraryApplication.domain.*;
import com.library.LibraryApplication.integration.BookApplicationRestClient;
import com.library.LibraryApplication.integration.EmailSender;
import com.library.LibraryApplication.jms.IJMSSender;
import com.library.LibraryApplication.repository.CustomerRepository;
import com.library.LibraryApplication.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibraryService {

    Logger logger= LoggerFactory.getLogger(LibraryService.class);

    @Autowired
    private LibraryConfigurations configurations;

    //Books are stored in key=title, value=book
    private Map<String, BookDTO> books=new HashMap<>();

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookApplicationRestClient client;

    @Autowired
    private IJMSSender ijmsSender;

    @Autowired
    private EmailSender emailSender;

    public Reservation checkoutBook(int custId, String title, double initialPayment, String op){
        Optional<Customer> cust=findById(custId);
        if(cust.isPresent()){
            BookDTO book=books.get(title);
            if(book !=null){
                Date dateOfCheckout=null;
                Date dateDue=null;
                Status status=Status.PENDING;
                String scancode=null;
                BookCopyDTO copy=client.getAvailableCopyByIsbn(book.getIsbn());  //Call Rest to check availability of this book
                if(op.equalsIgnoreCase("checkout")){
                    String[] borrowed = cust.get().getAccount().getBorrowedBooksScanCodes().split(",");
                    if(borrowed.length >= configurations.getMaxNoOfBooksPerCustomer()){
                        logger.info("You cannot borrow more than 4 books at the same time");
                        return null;
                    }
                    if(copy !=null){
                        dateOfCheckout = new Date();
                        status = Status.OPEN;
                        dateDue=getDueDate(configurations.getMaxCheckoutDays());
                        scancode = copy.getScanCode();
                    }
                }

                Reservation reservation=new Reservation(book.getIsbn(),scancode,
                new Date(), dateOfCheckout,dateDue, null, status,initialPayment);
                Customer customer=cust.get();
                customer.getReservation().add(reservation);
                Account acc=customer.getAccount();

                if(status.equals(Status.OPEN)){
                    acc.setBorrowedBooksScanCodes(copy.getScanCode());
                }else{
                    acc.setReservedBooksIsbnList(book.getIsbn());
                }

                customer.setAccount(acc);
                customerRepository.save(customer);

                if(status.equals(Status.OPEN)){ //If a book was checkedout, Update the BookApplication.
                    updateBookApplication(book, copy);
                }
                return reservation;
            }
        }
        return null; //Either customer or book is not found
    }

    public Reservation returnBook(int custId, String scancode, double amountDue){

         Reservation res=reservationRepository.findByScancodeAndStatus(scancode, Status.OPEN).get();
         res.setPaymentMade(amountDue);
         res.setStatus(Status.CLOSED);
         res.setDateClosed(new Date());

         Customer cust=customerRepository.findById(custId).get();
         Account acc=cust.getAccount();
         acc.setReturnedBooksIsbnList(res.getIsbn());
         acc.subtractAmountDueToCustomer(amountDue);
         cust.getReservation().add(res);
         cust.setAccount(acc);
         customerRepository.save(cust);

        //Call Rest to update the returned book
        //Check a customer that reserved this book and notify them
        BookDTO bk=client.searchBooks(scancode, "scancode");
        Integer customerId =reservationRepository.findFirstByIsbnAndStatus(bk.getIsbn(),Status.PENDING).get();
        Optional<Customer> newCust=customerRepository.findById(customerId);
        emailSender.sendEmail(newCust.get().getEmail(), "Your reserved book '"+bk.getTitle()+"' is now available.");//send email to cust
        updateBookApplication(bk, new BookCopyDTO(scancode, true));
        return res;
    }




    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }
    public Customer updateCustomer(Customer cust){
        Optional<Customer> cu=customerRepository.findByEmailIgnoreCase(cust.getEmail());
        if(cu.isPresent()){
            customerRepository.save(cust);
            return cust;
        }
        return null;
    }

    public void deleteCustomer(int id){
        Optional<Customer> cust=customerRepository.findById(id);
        if(cust.isPresent()){
            customerRepository.delete(cust.get());
        }
    }

    public Optional<Customer> findById(int id){
        Optional<Customer> cust = customerRepository.findById(id);
        if(cust.isPresent()){
            return cust;
        }
        return Optional.empty();
    }

    public Optional<Customer> findByName(String name){
        Optional<Customer> cust = customerRepository.findByFirstNameIgnoreCase(name);
        if(cust.isPresent()){
            return cust;
        }
        return Optional.empty();
    }

    public Optional<Customer> findByEmail(String email){
        Optional<Customer> cust = customerRepository.findByEmailIgnoreCase(email);
        if(cust.isPresent()){
            return cust;
        }
        return Optional.empty();
    }

    public Customers findAll(){
        List<Customer> list = customerRepository.findAll();
        if(!list.isEmpty()) {
            Customers cust=new Customers();
            cust.setCustomers(list);
           return cust;
        }
        return null;
    }

    public BookDTO findBookByTitle(String title){
        return books.get(title);
    }

    public BookDTO findBookByIsbn(String isbn){
        BookDTO book=null;
        for(BookDTO bookObj: books.values()){
            if(bookObj.getIsbn().equalsIgnoreCase(isbn)){
                return bookObj;
            }
        }
        return null;
    }

    public Date getDueDate(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, days);
        Date modifiedDate = c.getTime();
        return modifiedDate;
    }

    public void updateBookApplication(BookDTO book, BookCopyDTO copy){
        copy.changeAvailability();
        List<BookCopyDTO> copies=new ArrayList<>();
        copies.add(copy);
        book.setCopies(copies);
        client.updateBook(book);
    }

    void setBooks(Collection<BookDTO> updates) {
        Map<String, BookDTO> booksUpdate=new HashMap<>();
        updates.forEach(book -> {
            booksUpdate.put(book.getTitle(), book);
        });
        this.books = booksUpdate;
    }

    @EventListener
    public void loadBooks(Books response) {
        setBooks(response.getBooks());
        logger.info("Latest books has been loaded, total books:"+books.size());
    }
}
