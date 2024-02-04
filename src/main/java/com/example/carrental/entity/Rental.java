package com.example.carrental.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;

    public Car getCar() {
        return null;
    }

    public LocalDate getstartDate() {
        return null;
    }

    public LocalDate getendDate() {
        return null;
    }
}
