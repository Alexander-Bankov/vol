package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
@Entity
@Table(name = "action")
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_id")
    private BigInteger actionId;

    @Getter
    @Setter
    @Column(name = "action_name")
    private String actionName;

    @Getter
    @Setter
    @Column(name = "action_start")
    private LocalDate actionStart;

    @Getter
    @Setter
    @Column(name = "action_end")
    private String actionEnd;

    @Getter
    @Setter
    @Column(name = "status")
    private String status;

    @Getter
    @Setter
    @Column(name = "events")
    private String events;
}
