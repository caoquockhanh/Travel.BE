package com.MyTravel.mytravel.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "bookTour")
public class BookTour {

    @Id
    private String id;

    private User user;

    private Tour tour;

    private Number child;

    private Number adult;

    private String note;

    private BigDecimal totalPrice;

    private Boolean isCanceled;

    public BookTour() {
    }

    public BookTour(User user, Tour tour, Number child, Number adult, String note, BigDecimal totalPrice, Boolean isCanceled) {
        this.user = user;
        this.tour = tour;
        this.child = child;
        this.adult = adult;
        this.note = note;
        this.totalPrice = totalPrice;
        this.isCanceled = isCanceled;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Number getChild() {
        return child;
    }

    public void setChild(Number child) {
        this.child = child;
    }

    public Number getAdult() {
        return adult;
    }

    public void setAdult(Number adult) {
        this.adult = adult;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }
}
