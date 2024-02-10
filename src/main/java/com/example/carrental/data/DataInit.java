package com.example.carrental.data;

import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.RoleRepository;
import com.example.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarRepository carRepository;

    @Override
    public void run(String... args) {
        initData();
    }

    private void initData() {

        // Dodawanie samochodów do repozytorium
        carRepository.saveAll(List.of(
                Car.builder().name("BMW").model("X5").yearOfProduction("2022").engine("3.0").imgUrl("https://example.com/bmw-x5.jpg").available(true).build(),
                Car.builder().name("Audi").model("A6").yearOfProduction("2021").engine("2.0").imgUrl("https://example.com/audi-a6.jpg").available(true).build(),
                Car.builder().name("Mercedes").model("C-Class").yearOfProduction("2020").engine("2.0").imgUrl("https://example.com/mercedes-c-class.jpg").available(false).build()
        ));

        // Kod pozostały bez zmian
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role newAdminRole = new Role();
                    newAdminRole.setName("ADMIN");
                    return roleRepository.save(newAdminRole);
                });

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setEmail("admin@example.com");
        adminUser.setRoles(Set.of(adminRole));
        userRepository.save(adminUser);

        Role clientRole = roleRepository.findByName("CLIENT")
                .orElseGet(() -> {
                    Role newClientRole = new Role();
                    newClientRole.setName("CLIENT");
                    return roleRepository.save(newClientRole);
                });

        User clientUser = new User();
        clientUser.setUsername("client");
        clientUser.setPassword(passwordEncoder.encode("client123"));
        clientUser.setEmail("client@example.com");
        clientUser.setRoles(Set.of(clientRole));
        userRepository.save(clientUser);
    }
}
