package com.example.webappvolunteer.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.example.webappvolunteer.dto.LoginRequest;
import com.example.webappvolunteer.dto.VolunteerDto;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.enums.Role;
import com.example.webappvolunteer.repository.UserRepository;
import com.example.webappvolunteer.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.net.URI;

//import static com.example.webappvolunteer.config.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/vol")
public class RegistrController {
    private final UserRepository userRepository;
    private final CustomUserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;

    public RegistrController(UserRepository userRepository, CustomUserDetailService userDetailService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerVolunteer(@RequestBody VolunteerDto volunteer) {
        Boolean us = userDetailService.register(volunteer);
        if (us) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Волонтер успешно зарегистрирован!");
        } else {
            return ResponseEntity.badRequest().body("Такой пользователь уже есть");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            HttpSession existingSession = request.getSession(false);
            if (existingSession != null) {
                // Удаляем предыдущую сессию
                existingSession.invalidate();
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getMail(),
                            loginRequest.getPasswords()
                    )
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("userEmail", loginRequest.getMail());

            // Вывод информации о текущем пользователе
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println(authentication.isAuthenticated());

            return ResponseEntity.ok("Авторизация успешна");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        try{
            HttpSession session = request.getSession(false); // Получаем текущую сессию
            String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
            if (mail == null) {
                throw new IllegalStateException("Пользователь не авторизован");
            }
            if (session != null) {
                // Удаляем предыдущую сессию
                session.invalidate();
            }
            return ResponseEntity.ok().body("Logout session");

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("error");
        }
    }


}
