package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dto.ChangeVolunteerDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.dto.ShowApplicationDto;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Volunteer getPersonalInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String eMail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (eMail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        Optional<Volunteer> optionalVolunteer = userRepository.findByEmail(eMail);
        return optionalVolunteer.orElse(null);
    }

    @PutMapping("/updateByMail")
    public ResponseEntity updateVolunteer(@RequestBody ChangeVolunteerDto changeVolunteerDto, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false); // Получаем текущую сессию
            String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
            if (mail == null) {
                throw new IllegalStateException("Пользователь не авторизован");
            }

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
                    mail // Используем email для обновления
            );
            return ResponseEntity.ok().body("Данные успешно изменены");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Не удалось изменить данные");
        }
    }
    @GetMapping("/get-application-info")
    public List<ShowApplicationDto> getApplicationInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String eMail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (eMail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        List<Object[]> results = userRepository.ShowApplication(eMail);
        List<ShowApplicationDto> applicationDtos = new ArrayList<>();

        for (Object[] result : results) {
            ShowApplicationDto dto = new ShowApplicationDto(
                    (String) result[0], // action_name
                    (String) result[1], // event_name
                    (String) result[2], // e_mail
                    (String) result[3]  // status_application
            );
            applicationDtos.add(dto);
        }
        return applicationDtos;
    }



}
