package com.library.LibraryApplication.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int customerNumber;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(cascade=CascadeType.MERGE)
    @JoinColumn(name="cust")
    private Collection<Reservation> reservation= new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Account account =new Account();

    public Customer() {
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Collection<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Collection<Reservation> reservation) {
        this.reservation = reservation;
    }
}
