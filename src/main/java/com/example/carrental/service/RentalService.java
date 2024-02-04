package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.exception.InvalidDataException;
import com.example.carrental.exception.OperationFailedException;
import com.example.carrental.entity.Rental;
import com.example.carrental.exception.UnavailableCarException;
import com.example.carrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void saveRental(Rental rental) {
        // Rental data validation
        if (!isRentalPeriodValid(rental)) {
            throw new InvalidDataException("Invalid rental data.");
        }

        // Business logic
        if (!isCarAvailable(rental.getCar(), rental.getstartDate(), rental.getendDate())) {
            throw new UnavailableCarException("Car is not available for the selected period.");
        }

        // Save to the database
        try {
            rentalRepository.save(rental);
        } catch (DataIntegrityViolationException e) {
            throw new OperationFailedException("Error while saving rental to the database.", e);
        }
    }
    private boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        List<Rental> rentals = rentalRepository.findByCarId(car.getId());

        for (Rental existingRental : rentals) {
            if (startDate.isBefore(existingRental.getendDate()) &&
                    endDate.isAfter(existingRental.getstartDate())) {
                return false; // Car not available for the given period
            }
        }
        return true; // Car available
    }
    private boolean isRentalPeriodValid(Rental rental) {
        return rental.getstartDate() != null &&
                rental.getendDate() != null &&
                rental.getstartDate().isBefore(rental.getendDate()) &&
                rental.getstartDate().isAfter(LocalDate.now());
    }
}
