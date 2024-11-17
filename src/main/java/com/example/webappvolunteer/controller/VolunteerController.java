package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dto.ChangeVolunteerDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final UserRepository userRepository;

    public VolunteerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get-personal-info")
    public Volunteer getPersonalInfo() {
        String eMail = "glor@mail.ru";
        Volunteer person = new Volunteer();
        Optional<Volunteer> optionalVolunteer = userRepository.findByEmail(eMail);
        return optionalVolunteer.orElse(null);
    }
    @PutMapping("/updateByMail")
    public ResponseEntity updateVolunteer(@RequestBody ChangeVolunteerDto changeVolunteerDto) {
        try {
            String mail = "glor@mail.ru";
            // Обновляем сущность волонтера с полученными данными
            userRepository.updateVolunteer(
                    changeVolunteerDto.getLastName(),
                    changeVolunteerDto.getFirstName(),
                    changeVolunteerDto.getSecondName(),
                    changeVolunteerDto.getBirthdate(),
                    changeVolunteerDto.getAddress(),
                    changeVolunteerDto.getPhone(),
                    changeVolunteerDto.getVolunteerInfo(),
                    changeVolunteerDto.getLanguage(),
                    mail // Используем mail для обновления
            );
            return ResponseEntity.ok().body("Данные успешно изменены");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Не удалось изменить данные");
        }
    }
}
