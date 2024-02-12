package com.example.carrental.controller;

import com.example.carrental.entity.Role;
import com.example.carrental.entity.User;
import com.example.carrental.repository.RoleRepository;
import com.example.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "redirect:/register?error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> clientRole = roleRepository.findByName("CLIENT");

        user.setRoles(new HashSet<>(Arrays.asList(clientRole.orElse(null))));

        userRepository.save(user);

        return "redirect:/login?success";
    }
}