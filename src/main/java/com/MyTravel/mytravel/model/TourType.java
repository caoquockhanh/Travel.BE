package com.MyTravel.mytravel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tourtype")
public class TourType {
    @Id
    private String id;

    private Type name;

    public TourType() {

    }

    public TourType(Type name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getName() {
        return name;
    }

    public void setName(Type name) {
        this.name = name;
    }
}
