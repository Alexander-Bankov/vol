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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filter")
public class FilterController {
    private final ActionRepository actionRepository;
    private final EventRepository eventRepository;

    public FilterController(ActionRepository actionRepository, EventRepository eventRepository) {
        this.actionRepository = actionRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/status-actions")
    public ResponseEntity<List<ActionInfoDto>> getFilterStatusActions(@RequestParam String status) {
        List<Object[]> results =null;

        // Фильтрация по статусу, если статус указан
        if (status != null) {
            results = actionRepository.filterStatus(status);
        }
        // Преобразуем результаты в DTO
        List<ActionInfoDto> actionInfoDtos = new ArrayList<>();
        if (results != null) {
            for (Object[] result : results) {
                String actionName = (String) result[0];
                // Преобразуем java.sql.Date в LocalDate
                LocalDate actionStart = ((java.sql.Date) result[1]).toLocalDate();
                LocalDate actionEnd = ((java.sql.Date) result[2]).toLocalDate();
                String statuss = (String) result[3];
                String eventNames = (String) result[4];
                ActionInfoDto dto = new ActionInfoDto(actionName, actionStart, actionEnd, statuss, null);
                dto.setEventNamesFromString(eventNames); // Преобразуем строку в список
                actionInfoDtos.add(dto);
            }
        }

        return ResponseEntity.ok(actionInfoDtos);
    }

    @GetMapping("/date-actions")
    public ResponseEntity<List<ActionInfoDto>> getDateFilterActions(@RequestParam LocalDate dateStart,
                                                                    @RequestParam LocalDate dateEnd) {
        List<Object[]> results =null;
        // Фильтрация по дате, если даты указаны
        if (dateStart != null && dateEnd != null) {
            results = actionRepository.filterDateRange(dateStart, dateEnd);
        }
        // Преобразуем результаты в DTO
        List<ActionInfoDto> actionInfoDtos = new ArrayList<>();
        if (results != null) {
            for (Object[] result : results) {
                String actionName = (String) result[0];
                // Преобразуем java.sql.Date в LocalDate
                LocalDate actionStart = ((java.sql.Date) result[1]).toLocalDate();
                LocalDate actionEnd = ((java.sql.Date) result[2]).toLocalDate();
                String statuss = (String) result[3];
                String eventNames = (String) result[4];
                ActionInfoDto dto = new ActionInfoDto(actionName, actionStart, actionEnd, statuss, null);
                dto.setEventNamesFromString(eventNames); // Преобразуем строку в список
                actionInfoDtos.add(dto);
            }
        }

        return ResponseEntity.ok(actionInfoDtos);
    }

    @GetMapping("/max-vol-count-events")
    public ResponseEntity<List<EventInfoDto>> getFilterMaxVolCountEvents(@RequestParam String actionName,
                                                                         @RequestParam Integer maxVolCount) {

        List<Object[]> results = null;

        // Фильтрация по максимальному количеству волонтеров, если указано
        if (maxVolCount != null) {
            results = eventRepository.filterMaxVolunteerCount(actionName,maxVolCount);
        }
        // Преобразование результатов в DTO
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
    @GetMapping("/date-events")
    public ResponseEntity<List<EventInfoDto>> getFilterDateEvents(@RequestParam String actionName,
                                                                  @RequestParam LocalDateTime dateStart,
                                                                  @RequestParam LocalDateTime dateEnd) {

        List<Object[]> results = null;
        // Фильтрация по дате, если указаны даты
        if (dateStart != null && dateEnd != null) {
            results = eventRepository.filterDateRange(actionName,dateStart,dateEnd);
        }
        // Преобразование результатов в DTO
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
