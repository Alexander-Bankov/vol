package com.example.webappvolunteer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private BigInteger eventId;

    @Getter
    @Setter
    @Column(name = "event_name")
    private String eventName;

    @Getter
    @Setter
    @Column(name = "place")
    private String place;

    @Getter
    @Setter
    @Column(name = "date_and_time_start")
    private LocalDateTime startTime;

    @Getter
    @Setter
    @Column(name = "date_and_time_end")
    private LocalDateTime endTime;

    @Getter
    @Setter
    @Column(name = "volunteer_count")
    private Integer volunteerCount;

    @Getter
    @Setter
    @Column(name = "max_volunteer_count")
    private Integer maxVolunteerCount;

    @Getter
    @Setter
    @Column(name = "actionName")
    private String actionName;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL) // Каскадное удаление
    @JoinColumn(name = "action_id", referencedColumnName = "action_id", nullable = false)
    private Action action; // Ссылка на сущность Action


}
