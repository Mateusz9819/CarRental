package com.example.carrental.controller;

import com.example.carrental.Entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    }

    @PostMapping("/addItem")
    private String addItem(Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/adminPage")
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

    @GetMapping("/")
    public String defaultPage() {
        return "index";
    }
}
