package com.example.carrental.controller;

import com.example.carrental.entity.Rental;
import com.example.carrental.exception.*;
import com.example.carrental.repository.RentalRepository;
import com.example.carrental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Rental>> rentalHistory(@PathVariable Long userId) {
        List<Rental> history = rentalRepository.findByUserId(Math.toIntExact(userId));
        return ResponseEntity.ok(history);
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentCar(@RequestBody Rental rental) {
        try {
            rentalService.saveRental(rental);
            return ResponseEntity.ok("Rental confirmed!");
        } catch (UnavailableCarException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (OperationFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while renting a car.");
        }
    }
}

