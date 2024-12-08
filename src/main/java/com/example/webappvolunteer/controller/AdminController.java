package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.dto.ShowApplicationDto;
import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.entity.Volunteer;
import com.example.webappvolunteer.enums.ActionStatus;
import com.example.webappvolunteer.repository.ActionRepository;
import com.example.webappvolunteer.repository.ApplicationRepository;
import com.example.webappvolunteer.repository.EventRepository;
import com.example.webappvolunteer.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final EventRepository eventRepository;
    private final ApplicationRepository applicationRepository;

    public AdminController(UserRepository userRepository,ActionRepository actionRepository,EventRepository eventRepository, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
        this.eventRepository = eventRepository;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping("/get-application-info")
    public List<ShowApplicationDto> getApplicationInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String eMail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (eMail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        List<Object[]> results = applicationRepository.ShowApplication();
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
    @PostMapping("/update-status")
    public ResponseEntity<String> updateApplication(@RequestParam String actionName,
                                                    @RequestParam String eventName,
                                                    @RequestParam String mail,
                                                    @RequestParam String status,
                                                    HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String eMail = session != null ? (String) session.getAttribute("userEmail") : null;
        if (eMail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }
        Long mailId = userRepository.findIdByEmail(mail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long actionId = actionRepository.findActionIdByName(actionName)
                .orElseThrow(() -> new RuntimeException("Action not found"));
        Long eventId = eventRepository.findEventIdByName(eventName)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Long applicationId = applicationRepository.findApplicationIdByEmailAndActionAndEvent(mailId, actionId, eventId);
        if (applicationId == null) {
            return ResponseEntity.status(404).body("Заявка не найдена");
        }

        ActionStatus actionStatus;
        switch (status.toUpperCase()) {
            case "ПОДТВЕРДИТЬ":
                actionStatus = ActionStatus.ACCEPT;
                break;
            case "ОТКЛОНИТЬ":
                actionStatus = ActionStatus.REJECT;
                break;
            default:
                return ResponseEntity.badRequest().body("Некорректный статус");
        }

        applicationRepository.updateStatus(applicationId, actionStatus.name());
        return ResponseEntity.ok("Статус обновлен");
    }

    @GetMapping("/get-action-by-action-name")
    public ResponseEntity<List<ActionInfoDto>> getActionByActionName(@RequestParam("actionName") String actionNames) {
        List<Object[]> results = actionRepository.findActionInfoByActionName(actionNames);
        List<ActionInfoDto> actionInfoDtos = new ArrayList<>();

        for (Object[] result : results) {
            String actionName = (String) result[0];
            // Преобразуем java.sql.Date в LocalDate
            LocalDate actionStart = ((java.sql.Date) result[1]).toLocalDate();
            LocalDate actionEnd = ((java.sql.Date) result[2]).toLocalDate();
            String status = (String) result[3];
            String eventNames = (String) result[4];
            ActionInfoDto dto = new ActionInfoDto(actionName, actionStart, actionEnd, status, null);
            dto.setEventNamesFromString(eventNames); // Преобразуем строку в список
            actionInfoDtos.add(dto);
        }
        return ResponseEntity.ok(actionInfoDtos);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventInfoDto>> getEventByActionName() {
        List<EventInfoDto> eventInfoDtos = eventRepository.findAllEvents()
                .stream()
                .map(result -> new EventInfoDto(
                        (String) result[0], // eventName
                        (String) result[1], // actionName
                        (String) result[2], // place
                        ((java.sql.Timestamp) result[3]).toLocalDateTime(), // startTime
                        ((java.sql.Timestamp) result[4]).toLocalDateTime(), // endTime
                        (Integer) result[5], // volunteerCount
                        (Integer) result[6] // maxVolunteerCount
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventInfoDtos);
    }
    @GetMapping("/events-by-name")
    public ResponseEntity<List<EventInfoDto>> getAllEvents(@RequestParam("eventName") String eventName) {
        List<EventInfoDto> eventInfoDtos = eventRepository.findEventsByEventName(eventName)
                .stream()
                .map(result -> new EventInfoDto(
                        (String) result[0], // eventName
                        (String) result[1], // actionName
                        (String) result[2], // place
                        ((java.sql.Timestamp) result[3]).toLocalDateTime(), // startTime
                        ((java.sql.Timestamp) result[4]).toLocalDateTime(), // endTime
                        (Integer) result[5], // volunteerCount
                        (Integer) result[6] // maxVolunteerCount
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventInfoDtos);
    }

    @GetMapping("/get-personal-info")
    public ResponseEntity<Volunteer> getVolunteerByMail(@RequestParam("mail")  String mail) {
        Optional<Volunteer> optionalVolunteer = userRepository.findByEmail(mail);
        return optionalVolunteer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
//
        @GetMapping("/get-all-volunteer")
        public List<Volunteer> getAllVolunteers(HttpServletRequest request) {
            return userRepository.findAllVolunteers()
                    .orElse(Collections.emptyList()); // Если нет волонтеров, вернуть пустой список
        }
}
