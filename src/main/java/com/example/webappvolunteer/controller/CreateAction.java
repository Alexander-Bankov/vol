package com.example.webappvolunteer.controller;


import com.example.webappvolunteer.dto.ActionFilterDto;
import com.example.webappvolunteer.dto.ActionInfoDto;
import com.example.webappvolunteer.dto.EventInfoDto;
import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.entity.GuideAction;
import com.example.webappvolunteer.repository.ActionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/create-action")
public class CreateAction {

    private final ActionRepository actionRepository;

    public CreateAction(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }
    @PostMapping("/full-action")
    public ResponseEntity createAction(@RequestBody Action createAction) {
        try {
            actionRepository.save(createAction);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update-action")
    public ResponseEntity updateAction(@RequestBody Action createAction) {
        try {
            actionRepository.save(createAction);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/actions")
    public ResponseEntity<List<ActionInfoDto>> getAllEvents(@RequestParam("actionName") String actionNames) {
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

    @GetMapping("/all-actions")
    public ResponseEntity<List<ActionInfoDto>> getAllActions() {
        List<Object[]> results = actionRepository.getAllACtion();
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







}
