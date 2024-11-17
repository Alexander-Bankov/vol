package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.repository.ActionRepository;
import com.example.webappvolunteer.repository.ApplicationRepository;
import com.example.webappvolunteer.repository.EventRepository;
import com.example.webappvolunteer.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping("/create-application")
public class CreateApplicationController {

    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final EventRepository eventRepository;
    private final ApplicationRepository applicationRepository;

    public CreateApplicationController(UserRepository userRepository,ActionRepository actionRepository,EventRepository eventRepository, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
        this.eventRepository = eventRepository;
        this.applicationRepository = applicationRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createApplication(@RequestParam String actionName,
                                                    @RequestParam String eventName) {
        String eMail = "glor@mail.ru";

        Optional<Long> optionalVolunteer = userRepository.findIdByEmail(eMail);
        Optional<Long> optionalAction = actionRepository.findActionIdByName(actionName);
        Optional<Long> optionalEvent = eventRepository.findEventIdByName(eventName);

        if (optionalVolunteer.isPresent() && optionalAction.isPresent() && optionalEvent.isPresent()) {
            BigInteger volunteerId = BigInteger.valueOf(optionalVolunteer.get());
            BigInteger actionId = BigInteger.valueOf(optionalAction.get());
            BigInteger eventId = BigInteger.valueOf(optionalEvent.get());

            // Создаем новую заявку
            Application application = new Application();
            application.setEventId(eventId);
            application.setActionId(actionId);
            application.setVolunteerId(volunteerId);
            application.setStatus(false); // или true, в зависимости от вашего логики

            // Сохраняем заявку
            applicationRepository.save(application);
            return ResponseEntity.ok("Application created successfully.");
        }

        return ResponseEntity.badRequest().body("Failed to create application. One or more IDs not found.");
    }
}
