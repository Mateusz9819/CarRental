package com.example.carrental.security;

import com.example.carrental.Entity.Role;
import com.example.carrental.Entity.User;
import com.example.carrental.repository.RoleRepository;
import com.example.carrental.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);

        String newSessionId = generateNewSessionId();
        String username = authentication.getName();
        logger.info("Updating session ID for user: " + username);
        logger.info("New session ID: " + newSessionId);
        userRepository.updateSessionId(username, newSessionId);
    }

    private String generateNewSessionId() {
        return UUID.randomUUID().toString();
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();

            if ("ADMIN".equals(authority)) {
                Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    Optional<Role> adminRole = roleRepository.findByName("ADMIN");
                    if (adminRole.isPresent()) {
                        return "adminPage";
                    }
                }
            } else if ("CLIENT".equals(authority)) {
                return "/userPage";
            }
        }

        return "/";
    }
}