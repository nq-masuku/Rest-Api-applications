package com.library.LibraryApplication.domain;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private int id;
    private String borrowedBooksScanCodes;
    private String reservedBooksIsbnList;
    private String returnedBooksIsbnList;
    private double amountDueToCustomer;

    public Account() {
    }

    public String getBorrowedBooksScanCodes() {
        return borrowedBooksScanCodes;
    }

    public void setBorrowedBooksScanCodes(String borrowedBooksScanCodes) {
        if(this.borrowedBooksScanCodes != null){
            this.borrowedBooksScanCodes += ","+borrowedBooksScanCodes;
        }else{
            this.borrowedBooksScanCodes = borrowedBooksScanCodes;
        }

    }

    public String getReservedBooksIsbnList() {
        return reservedBooksIsbnList;
    }

    public void setReservedBooksIsbnList(String reservedBooksIsbnList) {
        if(this.reservedBooksIsbnList != null){
            this.reservedBooksIsbnList += ","+reservedBooksIsbnList;
        }else{
            this.reservedBooksIsbnList = reservedBooksIsbnList;
        }
    }

    public String getReturnedBooksIsbnList() {
        return returnedBooksIsbnList;
    }

    public void setReturnedBooksIsbnList(String returnedBooksIsbnList) {
        if(this.returnedBooksIsbnList != null){
            this.returnedBooksIsbnList += ","+returnedBooksIsbnList;
        }else{
            this.returnedBooksIsbnList = returnedBooksIsbnList;
        }
    }

    public double getAmountDueToCustomer() {
        return amountDueToCustomer;
    }

    public void addAmountDueToCustomer(double amountDueToCustomer) {
        this.amountDueToCustomer += amountDueToCustomer;
    }
    public void subtractAmountDueToCustomer(double amountDueToCustomer) {
        this.amountDueToCustomer -= amountDueToCustomer;
    }

}
