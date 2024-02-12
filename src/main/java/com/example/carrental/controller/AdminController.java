package com.example.carrental.controller;

import com.example.carrental.entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final CarRepository carRepository;

    @Autowired
    public AdminController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    @RequestMapping("adminPage")
    @PreAuthorize("hasRole('ADMIN')")
    private String adminPage() {
        return "adminView/adminPage";
    }

    @GetMapping("/adminView/addCar")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCar() {
        return "adminView/addCar";
    }

    @PostMapping("/adminView/addCar")
    @PreAuthorize("hasRole('ADMIN')")
    private String addCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/adminView/removeCar")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeCar(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "adminView/removeCar";
    }

    @PostMapping("/adminView/removeCar")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeCar(@RequestParam Long carId) {
        carRepository.deleteById(carId);
        return "redirect:/adminView/removeCar";
    }

}
