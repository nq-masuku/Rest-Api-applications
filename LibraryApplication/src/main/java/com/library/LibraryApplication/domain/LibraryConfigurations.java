package com.library.LibraryApplication.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="")
public class LibraryConfigurations {
    private int maxNoOfBooksPerCustomer;
    private int maxCheckoutDays;
    private double lateReturnFeePerDay;

    public LibraryConfigurations() {
    }

    public int getMaxNoOfBooksPerCustomer() {
        return maxNoOfBooksPerCustomer;
    }

    public void setMaxNoOfBooksPerCustomer(int maxNoOfBooksPerCustomer) {
        this.maxNoOfBooksPerCustomer = maxNoOfBooksPerCustomer;
    }

    public int getMaxCheckoutDays() {
        return maxCheckoutDays;
    }

    public void setMaxCheckoutDays(int maxCheckoutDays) {
        this.maxCheckoutDays = maxCheckoutDays;
    }

    public double getLateReturnFeePerDay() {
        return lateReturnFeePerDay;
    }

    public void setLateReturnFeePerDay(double lateReturnFeePerDay) {
        this.lateReturnFeePerDay = lateReturnFeePerDay;
    }
}
