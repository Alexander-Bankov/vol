package com.example.webappvolunteer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private BigInteger eventId;

    @Getter
    @Setter
    @Column(name = "event_title")
    private String eventTitle;

    @Getter
    @Setter
    @Column(name = "event_place")
    private String eventPlace;

    @Getter
    @Setter
    @Column(name = "start_time")
    private LocalDate startTime;

    @Getter
    @Setter
    @Column(name = "end_time")
    private String endTime;

    @Getter
    @Setter
    @Column(name = "volunteer_count")
    private String volunteerCount;
}
