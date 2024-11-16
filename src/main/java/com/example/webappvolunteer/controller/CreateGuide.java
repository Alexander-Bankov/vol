package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.GuideAction;
import com.example.webappvolunteer.entity.GuideLanguage;
import com.example.webappvolunteer.repository.UserRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideActionRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideLanguageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-guide")
public class CreateGuide {

    private final GuideLanguageRepository guideLanguageRepository;
    private final GuideActionRepository guideActionRepository;

    public CreateGuide(GuideLanguageRepository guideLanguageRepository, GuideActionRepository guideActionRepository, UserRepository userRepository) {
        this.guideLanguageRepository = guideLanguageRepository;
        this.guideActionRepository = guideActionRepository;
    }
    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping("/language")
    public ResponseEntity<?> createLanguagesGuide(@RequestBody GuideLanguage guideLanguage) {
        // Проверка аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
        } else {
            System.out.println("User is not authenticated");
        }

        guideLanguageRepository.save(guideLanguage);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping("/action")
    public ResponseEntity<?> createActionGuide(@RequestBody GuideAction guideAction) {
        // Проверка аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
        } else {
            System.out.println("User is not authenticated");
        }

        guideActionRepository.save(guideAction);
        return ResponseEntity.ok().build();
    }
}
