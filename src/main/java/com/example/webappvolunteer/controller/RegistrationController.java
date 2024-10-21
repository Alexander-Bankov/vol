package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/getAllUsers")
    public List<Volunteer> getAllUsers() {
        List<Volunteer> list = userRepository.findAll();
        return list;
    }
    @PostMapping("/registration")
    public String registrationUser(@RequestBody Volunteer user) {
        //user.setRole(Role.VOLUNTEER);
        userRepository.save(user);
        return "ok";
    }

    @GetMapping("/getUser")
    public Volunteer getUser(@RequestParam String eMail) {
        Volunteer person = new Volunteer();
        Optional<Volunteer> optionalVolunteer = userRepository.findByEmail(eMail);
        return optionalVolunteer.orElse(null);
    }



}
