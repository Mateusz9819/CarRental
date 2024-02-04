package com.example.carrental.controller;

import com.example.carrental.Entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminControler {
    private final CarRepository carRepository;
    @Autowired
    public AdminControler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    @RequestMapping("adminPage")
    private String adminPage() {
        return "adminView/adminPage";
    }
    @GetMapping("/adminView/addCar")
    public String addCar() {
        return "adminView/addCar";
    }
    @PostMapping("/adminView/addCar")
    private String addCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }
    @GetMapping("/adminView/removeCar")
    public String removeCar(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "adminView/removeCar";
    }
    @PostMapping("/adminView/removeCar")
    public String removeCar(@RequestParam Long carId) {
        carRepository.deleteById(carId);
        return "redirect:/adminView/removeCar";
    }
}
