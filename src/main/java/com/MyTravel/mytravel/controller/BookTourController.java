package com.MyTravel.mytravel.controller;

import com.MyTravel.mytravel.exception.ApiException;
import com.MyTravel.mytravel.exception.ErrorCode;
import com.MyTravel.mytravel.model.BookTour;
import com.MyTravel.mytravel.model.Tour;
import com.MyTravel.mytravel.model.User;
import com.MyTravel.mytravel.payload.request.BookTourRequest;
import com.MyTravel.mytravel.repository.BookTourRepository;
import com.MyTravel.mytravel.repository.TourRepository;
import com.MyTravel.mytravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookTourController {

    @Autowired
    BookTourRepository bookTourRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TourRepository tourRepository;

    @GetMapping("/bookTour")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookTour>> getAllBookTour(@RequestParam(required = false) Boolean isCanceled) {
        try {
            List<BookTour> bookTours = new ArrayList<BookTour>();

            if(isCanceled == null ){
                bookTours.addAll(bookTourRepository.findAll());
            } else {
                bookTours.addAll(bookTourRepository.findByIsCanceled(isCanceled));
            }



            if (bookTours.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bookTours, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/bookTour/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookTour> getBookTourById(@PathVariable("id") String id) {
        Optional<BookTour> bookTourData = bookTourRepository.findById(id);

        if (!bookTourRepository.existsById(id))
            throw new ApiException(ErrorCode.BOOK_TOUR_NOT_FOUND);

        if (bookTourData.isPresent()) {
            return new ResponseEntity<>(bookTourData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/bookTour")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookTour> createBookTour(
            @RequestBody BookTourRequest bookTourRequest,
            @RequestParam("userId") String userId,
            @RequestParam("tourId") String tourId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

            Tour tour = tourRepository.findById(tourId)
                    .orElseThrow(() -> new ApiException(ErrorCode.TOUR_NOT_FOUND));

            var bookTour = new BookTour();
            bookTour.setUser(user);
            bookTour.setTour(tour);

            bookTour.setChild(bookTourRequest.getChild());
            bookTour.setAdult(bookTourRequest.getAdult());
            bookTour.setNote(bookTourRequest.getNote());
            bookTour.setTotalPrice(bookTourRequest.getTotalPrice());
            bookTour.setIsCanceled(bookTourRequest.getIsCanceled());

            BookTour createBook = bookTourRepository.save(bookTour);

            return new ResponseEntity<>(createBook, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookTour/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookTour> canceledTour(@PathVariable("id") String id) {
        Optional<BookTour> bookTourData = bookTourRepository.findById(id);

        if (!bookTourRepository.existsById(id))
            throw new ApiException(ErrorCode.BOOK_TOUR_NOT_FOUND);

        if (bookTourData.isPresent()) {
            BookTour _bookTour = bookTourData.get();
            _bookTour.setIsCanceled(true);
            return new ResponseEntity<>(bookTourRepository.save(_bookTour), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bookTour/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBookTour(@PathVariable("id") String id) {
        if (!bookTourRepository.existsById(id))
            throw new ApiException(ErrorCode.BOOK_TOUR_NOT_FOUND);

        try {
            bookTourRepository.deleteById(id);
            return ResponseEntity.ok("Tour book has been canceled!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
