package com.example.carrental.controller;

import com.example.carrental.Entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminPage")

public class AdminControler {

    private final CarRepository carRepository;

    @Autowired
    public AdminControler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    private String adminPage(Model model) {
        model.addAttribute("dateRangeForm", new DateRangeForm());
        return "adminPage";
    @RequestMapping("adminPage")
    @PreAuthorize("hasRole('ADMIN')")
    private String adminPage() {
        return "adminView/adminPage";
    }

    @PostMapping("/adminView/addCar")
    @PreAuthorize("hasRole('ADMIN')")
    private String addCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/adminView/adminPage")
    public String adminPage(){
        return "redirect:/cars";
    }
    @PostMapping("/filterByDate")
    public String filterByDate(@ModelAttribute("dateRangeForm") DateRangeForm dateRangeForm, Model model) {
        // Tutaj dodaj logikę obsługi filtrowania po dacie
        // dateRangeForm zawiera dane z formularza

        // Pobierz wyniki filtrowania i dodaj do modelu
        // Przykładowo:
        // List<Car> filteredCars = carRepository.findByDateRange(dateRangeForm.getStartDate(), dateRangeForm.getEndDate());
        // model.addAttribute("filteredCars", filteredCars);

        // Przekieruj na odpowiednią stronę z wynikami filtrowania
        return "redirect:/filteredResults";
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
