package com.example.carrental.security;

import com.example.carrental.Entity.Role;
import com.example.carrental.repository.RoleRepository;
import com.example.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
        Role adminRole = Role.createAdminRole();
        roleRepository.save(adminRole);
        Role clientRole = Role.createClientRole();
        roleRepository.save(clientRole);

        http
                .csrf(c-> c.disable())
                .cors(c-> c.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/adminView/adminPage")).hasAuthority("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/adminView/addCar")).hasAuthority("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/adminView/removeCar")).hasAuthority("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/adminView/userPage")).hasAuthority("CLIENT")

                                .anyRequest().authenticated()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(new CustomAuthenticationSuccessHandler(userRepository, roleRepository))
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                )
                .headers(headers->headers.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.maximumSessions(1).expiredUrl("/login?expired")
                );
        return http.build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
    }
}