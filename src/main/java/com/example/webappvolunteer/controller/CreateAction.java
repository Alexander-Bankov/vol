package com.example.webappvolunteer.controller;


import com.example.webappvolunteer.entity.Action;
import com.example.webappvolunteer.repository.ActionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            createAction.setEvents(null);
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
            createAction.setEvents(null);
            actionRepository.save(createAction);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
