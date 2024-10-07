package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dao.Volunteer;
import com.example.webappvolunteer.enums.Role;
import com.example.webappvolunteer.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/vol")
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody Volunteer user) {
        //user.setRole(Role.VOLUNTEER);
        userRepository.save(user);
        return "ok";
    }


}
