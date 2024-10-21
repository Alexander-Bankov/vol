package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_id")
    private BigInteger actionId;

    @Getter
    @Setter
    @Column(name = "action_title")
    private String actionTitle;

    @Getter
    @Setter
    @Column(name = "event_id")
    private String eventId;

    @Getter
    @Setter
    @Column(name = "start_date")
    private LocalDate startDate;

    @Getter
    @Setter
    @Column(name = "end_Date")
    private String endDate;

    @Getter
    @Setter
    @Column(name = "status")
    private String Status;
}
