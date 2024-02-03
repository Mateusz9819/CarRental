package com.example.carrental.controller;

import com.example.carrental.Entity.Car;
import com.example.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Controller
public class HomeController {
    private final CarRepository carRepository;
    @Autowired
    public HomeController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/")
    public String defaultPage() {

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/cars")
    public String cars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars";
    }
}
