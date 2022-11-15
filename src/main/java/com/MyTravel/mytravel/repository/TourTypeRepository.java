package com.MyTravel.mytravel.repository;

import com.MyTravel.mytravel.model.TourType;
import com.MyTravel.mytravel.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TourTypeRepository extends MongoRepository<TourType, String> {
    Optional<TourType> findByName(Type name);
}