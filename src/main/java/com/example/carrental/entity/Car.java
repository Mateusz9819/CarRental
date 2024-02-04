package com.example.carrental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Car {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private String yearOfProduction;
    private String engine;
    private String imgUrl;

    @OneToMany(mappedBy = "car")
    private List<Rental> rentals;

    public Car(Object o, String bmw, String m8, String number, String s, String url) {
    }
}
