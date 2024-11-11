package com.example.webappvolunteer.controller;

import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.repository.ActionRepository;
import com.example.webappvolunteer.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sorted")
public class SortedController {
    private final ActionRepository actionRepository;
    private final EventRepository eventRepository;

    public SortedController(ActionRepository actionRepository, EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.actionRepository = actionRepository;
    }


    @GetMapping("/actions")
    public ResponseEntity<List<ActionInfoDto>> getSortedActions(@RequestParam String nameSorted) {
        List<Object[]> results = null;

        switch (nameSorted) {
            case "name":
                results = actionRepository.getSortedByName();
                break;
            case "status":
                results = actionRepository.getSortedByStatus();
                break;
            case "date":
                results = actionRepository.getSortedByDate();
                break;
            default:
                results = actionRepository.getAllACtion(); // Возвращаем все действия по умолчанию
                break;
        }

        List<ActionInfoDto> actionInfoDtos = new ArrayList<>();
        if (results != null) {
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
        }
        return ResponseEntity.ok(actionInfoDtos);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventInfoDto>> getSortedEvents(@RequestParam String nameSorted, @RequestParam String nameAction) {
        List<Object[]> results;

        switch (nameSorted) {
            case "name":
                results = eventRepository.eventSortedByNameAsc(nameAction);
                break;
            case "volCount":
                results = eventRepository.eventSortedByMaxVolunteerCountDesc(nameAction);
                break;
            case "date":
                results = eventRepository.eventSortedByStartDateDesc(nameAction);
                break;
            default:
                results = eventRepository.findEventsByActionName(nameAction);
                break;
        }

        List<EventInfoDto> eventInfoDtos = results.stream()
                .map(result -> new EventInfoDto(
                        (String) result[0], // eventName
                        (String) result[1], // actionName
                        (String) result[2], // place
                        ((java.sql.Timestamp) result[3]).toLocalDateTime(), // startTime
                        ((java.sql.Timestamp) result[4]).toLocalDateTime(), // endTime
                        (Integer) result[5], // volunteerCount
                        (Integer) result[6]  // maxVolunteerCount
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventInfoDtos);
    }
}
