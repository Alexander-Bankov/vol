package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.GuideAction;
import com.example.webappvolunteer.entity.GuideLanguage;
import com.example.webappvolunteer.repository.UserRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideActionRepository;
import com.example.webappvolunteer.repository.guideRepository.GuideLanguageRepository;
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
    @PostMapping("/language")
    public void createLanguagesGuide(@RequestBody GuideLanguage guideLanguage){
        guideLanguageRepository.save(guideLanguage);
    }

    @PostMapping("/action")
    public void createLanguagesGuide(@RequestBody GuideAction guideAction){
        guideActionRepository.save(guideAction);
    }
}
