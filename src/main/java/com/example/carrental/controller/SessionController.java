package com.example.carrental.controller;

import com.example.carrental.Entity.User;
import com.example.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
public class SessionController {
    private UserRepository userRepository;
    @Autowired
    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession(Authentication authentication) {
        String username = authentication.getName(); // Aktualnie zalogowany użytkownik

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent() && optionalUser.get().getSessionId() != null) {
            if (isActiveSession(optionalUser.get().getSessionId())) {
                return ResponseEntity.ok("Sesja jest aktywna.");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sesja nie jest aktywna.");
    }

    private boolean isActiveSession(String sessionId) {
        // Sprawdź w bazie danych czy sesja jest aktywna
        // Tutaj musisz zaimplementować odpowiednie zapytanie SQL
        // Przykładowe zapytanie SQL:
        // SELECT * FROM USERS WHERE SESSION_ID = :sessionId AND CURRENT_TIMESTAMP < SESSION_EXPIRATION_TIME;
        return true; // Zwróć true, jeśli sesja jest aktywna, w przeciwnym razie false
    }
}
