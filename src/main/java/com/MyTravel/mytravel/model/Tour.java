package com.MyTravel.mytravel.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Document(collection = "tours")
public class Tour {

    @Id
    private String id;

    @NotBlank
    private String banner;

    @NotBlank
    private String tourName;

    @NotBlank
    private String tourPlace;

    @NotBlank
    private String introduce;

    @NotBlank
    private String tourPlan;

    @NotBlank
    private String phone;

    @NotBlank
    private String rating;

    @NotBlank
    private BigDecimal basePrice;

    @DBRef
    private Set<TourType> types = new HashSet<>();

    public Tour() {
    }

    public Tour(String banner, String tourName, String tourPlace, String introduce, String tourPlan, String phone, String rating, BigDecimal basePrice) {
        this.banner = banner;
        this.tourName = tourName;
        this.tourPlace = tourPlace;
        this.introduce = introduce;
        this.tourPlan = tourPlan;
        this.phone = phone;
        this.rating = rating;
        this.basePrice = basePrice;
    }

    public String getId() {
        return id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourPlace() {
        return tourPlace;
    }

    public void setTourPlace(String tourPlace) {
        this.tourPlace = tourPlace;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTourPlan() {
        return tourPlan;
    }

    public void setTourPlan(String tourPlan) {
        this.tourPlan = tourPlan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Set<TourType> getTypes() {
        return types;
    }

    public void setTypes(Set<TourType> types) {
        this.types = types;
    }
}
