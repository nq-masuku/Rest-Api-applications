package com.library.LibraryApplication.web;

import com.library.LibraryApplication.domain.Customer;
import com.library.LibraryApplication.domain.Reservation;
import com.library.LibraryApplication.service.Customers;
import com.library.LibraryApplication.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class LibraryApplicationController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/reservations")
    public ResponseEntity<?> updateReservation(
            @RequestParam(value="customernumber", required = false) Integer customernumber,
            @RequestParam(value="title", required = false) String title,
            @RequestParam(value="payment", required = false) Double payment,
            @RequestParam(value="scancode", required = false) String scancode,
            @RequestParam(value="operation", required = false) String operation) {

        Reservation res = null;
        if(operation.equalsIgnoreCase("returnBook")){
            res=libraryService.returnBook(customernumber,scancode,payment);
        }else {
            res = libraryService.checkoutBook(customernumber,title,payment, operation);
        }

        if(res == null){
            return new ResponseEntity<>(new CustomErrorType(
                    "Provided Customer or Book does not exist"), HttpStatus.NOT_FOUND);
        } return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer cust) {
        libraryService.addCustomer(cust);
        return new ResponseEntity<> (cust, HttpStatus.OK);
    }

    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer cust) {
        Customer results = libraryService.updateCustomer(cust);
        if(results == null){
            return new ResponseEntity<>(new CustomErrorType("Customer was not found for the requested update"), HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<> (cust, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        libraryService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> searchCustomer(
            @RequestParam(value="customernumber", required = false) Integer customernumber,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="email", required = false) String email) {

        CustomErrorType error=new CustomErrorType("Requested customer not available");
        if(customernumber != null){
            Optional<Customer> cust = libraryService.findById(customernumber);
            return responseEntityForOptional(cust,error);
        }
        if(name != null){
            Optional<Customer> cust = libraryService.findByName(name);
            return responseEntityForOptional(cust,error);
        }
        if(email != null){
            Optional<Customer> cust = libraryService.findByEmail(email);
            return responseEntityForOptional(cust,error);
        }
        return responseEntityForList(libraryService.findAll(), error);

    }

    public ResponseEntity<?> responseEntityForOptional(Optional<Customer> cust, CustomErrorType error){
        if(cust.isPresent()){
            return new ResponseEntity<>(cust.get(), HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> responseEntityForList(Customers customers, CustomErrorType error){
        if(customers != null){
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<?> welcomeMessage(){
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }
}
