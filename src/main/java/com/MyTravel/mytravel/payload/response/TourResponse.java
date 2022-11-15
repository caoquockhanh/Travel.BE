package com.MyTravel.mytravel.payload.response;

import java.math.BigDecimal;
import java.util.List;

public class TourResponse {

    private String id;

    private String banner;

    private String tourName;

    private String tourPlace;

    private String introduce;

    private String tourPlan;

    private String phone;

    private String rating;

    private BigDecimal basePrice;

    private List<String> tourTypes;

    public TourResponse(String id, String banner, String tourName, String tourPlace, String introduce, String tourPlan, String phone, String rating, BigDecimal basePrice, List<String> tourTypes) {
        this.id = id;
        this.banner = banner;
        this.tourName = tourName;
        this.tourPlace = tourPlace;
        this.introduce = introduce;
        this.tourPlan = tourPlan;
        this.phone = phone;
        this.rating = rating;
        this.basePrice = basePrice;
        this.tourTypes = tourTypes;
    }
}
