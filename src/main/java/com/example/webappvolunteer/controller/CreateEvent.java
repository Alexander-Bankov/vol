package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.dto.EventFilterDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.entity.Event;
import com.example.webappvolunteer.entity.GuideAction;
import com.example.webappvolunteer.repository.ActionRepository;
import com.example.webappvolunteer.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/create-event")
public class CreateEvent {
    private final EventRepository eventRepository;
    private final ActionRepository actionRepository;

    public CreateEvent(EventRepository eventRepository, ActionRepository actionRepository) {
        this.eventRepository = eventRepository;
        this.actionRepository = actionRepository;
    }

    @PostMapping("/full-event")
    public ResponseEntity createAction(@RequestBody Event createEvent) {
        Action action = new Action();
        try {
            // Получаем ID действия по имени
            Optional<Long> actionId = eventRepository.findActionIdByName(createEvent.getActionName());

            if (actionId.isPresent()) {
                // Если действие найдено, устанавливаем его в событие
                action = actionRepository.findById(actionId.get())
                        .orElseThrow(() -> new RuntimeException("Action not found"));
                createEvent.setAction(action);
            } else {
                // Если действие не найдено, возвращаем ответ с ошибкой
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Action not found");
            }

            LocalDate startActionDate = action.getActionStart();
            LocalDate endActionDate = action.getActionEnd();

            // Преобразуем LocalDate в LocalDateTime (устанавливаем время на начало или конец дня)
            LocalDateTime startActionDateTime = startActionDate.atStartOfDay();
            LocalDateTime endActionDateTime = endActionDate.atTime(23, 59, 59, 999999999);

            if(createEvent.getEndTime().isBefore(createEvent.getStartTime())) {
                throw new RuntimeException("Дата и время начала мероприятия не может быть позже конца");
            }

            // Проверка корректности дат
            if (createEvent.getStartTime().isBefore(startActionDateTime) || createEvent.getEndTime().isAfter(endActionDateTime)) {
                throw new RuntimeException("Неверные даты. " +
                                           "Дата начала мероприятия должна быть позже дата начала события. " +
                                           "Дата конца мероприятия должна быть раньше даты конца события");
            }
            // Проверка корректности дат
            if (createEvent.getMaxVolunteerCount()>20) {
                throw new RuntimeException("Максимальное количество волонтеров 20");
            }
            createEvent.setVolunteerCount(0);
            // Сохраняем событие в базе данных
            Event savedEvent = eventRepository.save(createEvent);
            return ResponseEntity.ok(savedEvent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }




    @GetMapping("/events")
    public ResponseEntity<List<EventInfoDto>> getAllEvents(@RequestParam("actionName") String actionName) {
        List<EventInfoDto> eventInfoDtos = eventRepository.findEventsByActionName(actionName)
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
}
