package com.MyTravel.mytravel.repository;

import com.MyTravel.mytravel.model.BookTour;
import com.MyTravel.mytravel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookTourRepository extends MongoRepository<BookTour, String> {

    Optional<BookTour> findById(String id);

    List<BookTour> findByIsCanceled(Boolean isCanceled);
}
