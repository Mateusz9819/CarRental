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
                new Car(null, "TESLA","MODEL 3","2023","ELECTRIC","https://edgecast-img.yahoo.net/mysterio/api/8048C6FF048348E265D37CF92A422C3DC9D106F585042254799FAC808D83BD67/autoblog/resizefill_w660_h372;quality_80;format_webp;cc_31536000;/https://s.aolcdn.com/commerce/autodata/images/USD20TSC032A021001.jpg"),
                new Car(null, "AUDI","A8","2020","3.0","https://wallpapercave.com/wp/wp4254264.jpg")
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