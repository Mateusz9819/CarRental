package com.example.carrental.controller;

import com.example.carrental.Entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class AdminControler {
    private final CarRepository carRepository;
@Autowired
    public AdminControler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    private String adminPage() {
        return "adminView/addCar";
    }
    @PostMapping
    private String addItem(Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }
}
