package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.entity.Application;
import com.example.webappvolunteer.enums.ActionStatus;
import com.example.webappvolunteer.repository.ActionRepository;
import com.example.webappvolunteer.repository.ApplicationRepository;
import com.example.webappvolunteer.repository.EventRepository;
import com.example.webappvolunteer.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
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
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createApplication(@RequestParam String actionName,
                                                    @RequestParam String eventName,
                                                    HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (mail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        Optional<Long> optionalVolunteer = userRepository.findIdByEmail(mail);
        Optional<Long> optionalAction = actionRepository.findActionIdByName(actionName);
        Optional<Long> optionalEvent = eventRepository.findEventIdsByName(eventName,actionName);

        if (optionalVolunteer.isPresent() && optionalAction.isPresent() && optionalEvent.isPresent()) {
            BigInteger volunteerId = BigInteger.valueOf(optionalVolunteer.get());
            BigInteger actionId = BigInteger.valueOf(optionalAction.get());
            BigInteger eventId = BigInteger.valueOf(optionalEvent.get());
            Optional<Integer> kolvoOpt = eventRepository.findActionKolvoByName(eventId.longValue());
            Optional<Integer> maxKolVoOpt = eventRepository.findActionMaxKolvoByName(eventId.longValue());
            Integer kolvo = kolvoOpt.orElse(0); // если значение отсутствует, возвращаем 0
            Integer maxKolVo = maxKolVoOpt.orElse(0);
            // Проверяем наличие предыдущей заявки на это событие и мероприятие
            List<BigInteger> existingApplications = applicationRepository.findApplicationIdsByEmailAndActionAndEvent(
                    optionalVolunteer.get(),
                    optionalAction.get(),
                    optionalEvent.get()
            );

            if (!existingApplications.isEmpty()) {
                //throw new RuntimeException();
                return ResponseEntity.badRequest().body("Заявка на это событие и мероприятие уже существует.");
            }
            if(kolvo +1 > maxKolVo) {
                //throw new RuntimeException();
                return ResponseEntity.badRequest().body("Максимальное количество волонтеров");
            }
            try{
                eventRepository.incrementVolunteerCount(eventId.longValue());

            } catch (Exception e){
                return ResponseEntity.badRequest().body("Максимальное количество волонтеров");
            }
            // Создаем новую заявку
            Application application = new Application();
            application.setEventId(eventId);
            application.setActionId(actionId);
            application.setVolunteerId(volunteerId);
            application.setStatus(ActionStatus.INPROCESS.name()); // или true, в зависимости от вашего логики

            // Сохраняем заявку
            applicationRepository.save(application);
            return ResponseEntity.ok("Application created successfully.");
        }

        return ResponseEntity.badRequest().body("Failed to create application. One or more IDs not found.");
    }

    @Transactional
    @DeleteMapping("/delete-application")
    public ResponseEntity<String> deleteApplication(
            @RequestParam String actionName,
            @RequestParam String eventName,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String eMail = session != null ? (String) session.getAttribute("userEmail") : null;

        if (eMail == null) {
            return ResponseEntity.status(401).body("Пользователь не авторизован");
        }
        Long mailId =userRepository.findIdByEmail(eMail)
                .orElseThrow(() -> new RuntimeException("Action not found"));
        Long actionId = actionRepository.findActionIdByName(actionName)
                .orElseThrow(() -> new RuntimeException("Action not found"));
        Long eventId = eventRepository.findEventIdByName(eventName)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<BigInteger> applicationIds = applicationRepository.findApplicationIdsByEmailAndActionAndEvent(mailId, actionId, eventId);
        if (actionId == null && eventId == null) {
            return ResponseEntity.status(404).body("Заявка не найдена");
        }

        // Удаляем заявки по ранее найденным идентификаторам
        for (BigInteger applicationId : applicationIds) {
            applicationRepository.deleteByApplicationId(applicationId);
        }
        eventRepository.deleteVolunteerCount(eventId);

        return ResponseEntity.ok("Заявки успешно удалены");
    }
}
