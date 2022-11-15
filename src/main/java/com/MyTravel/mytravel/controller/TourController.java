package com.MyTravel.mytravel.controller;

import com.MyTravel.mytravel.model.*;
import com.MyTravel.mytravel.exception.ApiException;
import com.MyTravel.mytravel.exception.ErrorCode;
import com.MyTravel.mytravel.payload.request.TourRequest;
import com.MyTravel.mytravel.repository.TourRepository;
import com.MyTravel.mytravel.repository.TourTypeRepository;
import com.MyTravel.mytravel.security.services.ImageService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TourController {

    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourTypeRepository tourTypeRepository;

    @Autowired
    ImageService imageService;


    @GetMapping("/tours")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Tour>> getAllTour(@RequestParam(required = false) String tourName) {
        try {
            List<Tour> tours = new ArrayList<Tour>();

            if (tourName == null)
                tourRepository.findAll().forEach(tours::add);
            else
                tourRepository.findByTourName(tourName).forEach(tours::add);

            if (tours.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tours/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Tour> getTourById(@PathVariable("id") String id) {
        Optional<Tour> tourData = tourRepository.findById(id);

        if (!tourRepository.existsById(id))
            throw new ApiException(ErrorCode.TOUR_NOT_FOUND);

        if (tourData.isPresent()) {
            return new ResponseEntity<>(tourData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tours")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tour> createTour(@RequestBody TourRequest tourRequest) {
        try {
            Tour tour = tourRepository.save(new Tour(
                    tourRequest.getBanner(),
                    tourRequest.getTourName(),
                    tourRequest.getTourPlace(),
                    tourRequest.getIntroduce(),
                    tourRequest.getTourPlan(),
                    tourRequest.getPhone(),
                    tourRequest.getRating(),
                    tourRequest.getBasePrice()));

            Set<String> strTypes = tourRequest.getTypes();
            Set<TourType> types = new HashSet<>();
            if (strTypes == null) {
                TourType type = tourTypeRepository.findByName(Type.NORMAL)
                        .orElseThrow(() -> new RuntimeException("Error: Type is not found."));
                types.add(type);
            }
            else {
                strTypes.forEach(type -> {
                    switch (type) {
                        case "sea":
                            TourType sea = tourTypeRepository.findByName(Type.SEA)
                                    .orElseThrow(() -> new RuntimeException("Error: Type is not found."));
                            types.add(sea);

                            break;
                        case "mount":
                            TourType mount = tourTypeRepository.findByName(Type.MOUNT)
                                    .orElseThrow(() -> new RuntimeException("Error: Type is not found."));
                            types.add(mount);

                            break;
                        case "nature":
                            TourType nature = tourTypeRepository.findByName(Type.NATURE)
                                    .orElseThrow(() -> new RuntimeException("Error: Type is not found."));
                            types.add(nature);
                            break;
                        default:
                            TourType normal = tourTypeRepository.findByName(Type.NORMAL)
                                    .orElseThrow(() -> new RuntimeException("Error: Type is not found."));
                            types.add(normal);
                    }
                });
            }
            tour.setTypes(types);
            tourRepository.save(tour);
            return new ResponseEntity<>(tour, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tours/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tour> updateTour(@PathVariable("id") String id, @RequestBody Tour tour) {
        Optional<Tour> tourData = tourRepository.findById(id);

        if (!tourRepository.existsById(id))
            throw new ApiException(ErrorCode.TOUR_NOT_FOUND);

        if (tourData.isPresent()) {
            Tour _tour = tourData.get();
            _tour.setTourName(tour.getTourName());
            _tour.setTourPlace(tour.getTourPlace());
            _tour.setIntroduce(tour.getIntroduce());
            _tour.setTourPlan(tour.getTourPlan());
            _tour.setPhone(tour.getPhone());
            _tour.setRating(tour.getRating());
            _tour.setBasePrice(tour.getBasePrice());
            _tour.setTypes(tour.getTypes());
            return new ResponseEntity<>(tourRepository.save(_tour), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tours/image")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(code = 404, message = "TOUR_NOT_FOUND"),
            @ApiResponse(code = 500, message = "IMAGE_UPLOAD_FAILED")
    })
    public Tour updateTourImage(@RequestParam("id") String id, @RequestPart("image") MultipartFile image) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.TOUR_NOT_FOUND));
        try {
            if (tour.getBanner() != null) imageService.deleteImage(tour.getBanner());
            String imageFileName = imageService.uploadImage(image);
            tour.setBanner(imageFileName);
            return tourRepository.save(tour);
        } catch (IOException e) {
            throw new ApiException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }

    @DeleteMapping("/tours/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTour(@PathVariable("id") String id) {
        if (!tourRepository.existsById(id))
            throw new ApiException(ErrorCode.TOUR_NOT_FOUND);

        try {
            tourRepository.deleteById(id);
            return ResponseEntity.ok("Tour has been deleted!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "/tours/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getImage(@RequestParam("id") String id) throws IOException {
        Optional<Tour> tourData = tourRepository.findById(id);

        if (!tourRepository.existsById(id))
            throw new ApiException(ErrorCode.TOUR_NOT_FOUND);

        if (tourData.isPresent()) {
            Tour _tour = tourData.get();
            Path filePath = Paths.get("images/"+ _tour.getBanner());
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ);
                return in.readAllBytes();
        }
    }
        return new byte[0];
    }
}
