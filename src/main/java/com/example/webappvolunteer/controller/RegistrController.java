package com.example.webappvolunteer.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.example.webappvolunteer.dto.VolunteerDto;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.enums.Role;
import com.example.webappvolunteer.repository.UserRepository;
import com.example.webappvolunteer.service.CustomUserDetailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import static com.example.webappvolunteer.config.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/vol")
public class RegistrController {
    private final UserRepository userRepository;
    private final CustomUserDetailService userDetailService;

    public RegistrController(UserRepository userRepository, CustomUserDetailService userDetailService) {
        this.userRepository = userRepository;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/register")
    public String registerVolunteer(@RequestBody VolunteerDto volunteer) {
        Boolean us = userDetailService.register(volunteer);
        if(us){
            return "Волонтер успешно зарегистрирован!";
        }
        else return "такой пользователь уже есть";
    }
}
