package com.library.LibraryApplication.domain;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private int id;
    private String isbn;

    private String scancode;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Temporal(TemporalType.DATE)
    private Date dateOfCheckout;
    @Temporal(TemporalType.DATE)
    private Date dateDue;
    @Temporal(TemporalType.DATE)
    private Date dateClosed;
    private Status status;
    private double paymentMade;

    public Reservation() {
    }

    public Reservation(String isbn, String scancode, Date dateCreated, Date dateOfCheckout,
                       Date dateDue, Date dateClosed, Status status, double paymentMade) {
        this.isbn = isbn;
        this.scancode = scancode;
        this.dateCreated = dateCreated;
        this.dateOfCheckout = dateOfCheckout;
        this.dateDue = dateDue;
        this.dateClosed = dateClosed;
        this.status = status;
        this.paymentMade = paymentMade;
    }



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateOfCheckout() {
        return dateOfCheckout;
    }

    public void setDateOfCheckout(Date dateOfCheckout) {
        this.dateOfCheckout = dateOfCheckout;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getPaymentMade() {
        return paymentMade;
    }

    public void setPaymentMade(double paymentMade) {
        this.paymentMade += paymentMade;
    }

    public String getScancode() {
        return scancode;
    }

    public void setScancode(String scancode) {
        this.scancode = scancode;
    }
}
