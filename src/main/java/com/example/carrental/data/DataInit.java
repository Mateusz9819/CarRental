package com.example.carrental.data;
import com.example.carrental.Entity.Car;
import com.example.carrental.Entity.Role;
import com.example.carrental.Entity.User;
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

        carRepository.saveAll(List.of(
                new Car(null,"BMW","M8","2018","5.0","https://inv.assets.ansira.net/ChromeColorMatch/us/TRANSPARENT_cc_2024BMC910025_01_1280_475.png"),
                new Car(null, "BMW","M8","2018","5.0","https://inv.assets.ansira.net/ChromeColorMatch/us/TRANSPARENT_cc_2024BMC910025_01_1280_475.png"),
                new Car(null, "BMW","M8","2018","5.0","https://inv.assets.ansira.net/ChromeColorMatch/us/TRANSPARENT_cc_2024BMC910025_01_1280_475.png")
        ));

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