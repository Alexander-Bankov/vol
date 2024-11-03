package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.GuideAction;
import com.example.webappvolunteer.entity.GuideLanguage;
import com.example.webappvolunteer.repository.UserRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideActionRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideLanguageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get-guide")
public class GetAllGuide {
    private final GuideLanguageRepository guideLanguageRepository;
    private final GuideActionRepository guideActionRepository;

    public GetAllGuide(GuideLanguageRepository guideLanguageRepository, GuideActionRepository guideActionRepository, UserRepository userRepository) {
        this.guideLanguageRepository = guideLanguageRepository;
        this.guideActionRepository = guideActionRepository;
    }

    @GetMapping("/languages")
    public ResponseEntity<List<GuideLanguage>> getAllLanguages() {
        List<GuideLanguage> languages = guideLanguageRepository.findAll();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/actions")
    public ResponseEntity<List<GuideAction>> getAllActions() {
        List<GuideAction> actions = guideActionRepository.findAll();
        return ResponseEntity.ok(actions);
    }
}
