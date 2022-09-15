package com.library.LibraryApplication.service;


import com.library.LibraryApplication.domain.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class Customers {
    private Collection<Customer> customers = new ArrayList<>();

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> cust) {
        this.customers = cust;
    }
}
