package com.MyTravel.mytravel.repository;

import com.MyTravel.mytravel.model.Tour;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TourRepository extends MongoRepository<Tour, String> {
    Boolean existsByTourName(String tourName);

    List<Tour> findByTourName(String tourName);
}
