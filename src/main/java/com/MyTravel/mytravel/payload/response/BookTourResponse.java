package com.MyTravel.mytravel.payload.response;

import com.MyTravel.mytravel.model.Tour;
import com.MyTravel.mytravel.model.User;

import java.math.BigDecimal;

public class BookTourResponse {

    private String id;

    private User user;

    private Tour tour;

    private Number child;

    private Number adult;

    private String note;

    private BigDecimal totalPrice;

    private Boolean isCanceled;

    public BookTourResponse(String id, User user, Tour tour, Number child, Number adult, String note, BigDecimal totalPrice, Boolean isCanceled) {
        this.id = id;
        this.user = user;
        this.tour = tour;
        this.child = child;
        this.adult = adult;
        this.note = note;
        this.totalPrice = totalPrice;
        this.isCanceled = isCanceled;
    }
}
