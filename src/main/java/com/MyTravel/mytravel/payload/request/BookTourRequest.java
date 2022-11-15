package com.MyTravel.mytravel.payload.request;

import com.MyTravel.mytravel.model.Tour;
import com.MyTravel.mytravel.model.User;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class BookTourRequest {
    private User user;

    private Tour tour;

    private Number child;

    private Number adult;

    private String note;

    private BigDecimal totalPrice;

    private Boolean isCanceled;

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
