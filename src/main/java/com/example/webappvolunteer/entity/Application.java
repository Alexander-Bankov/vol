package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "application")
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id")
    private BigInteger applicationId;

    @Getter
    @Setter
    @Column(name = "event_id")
    private BigInteger eventId;

    @Getter
    @Setter
    @Column(name = "action_id")
    private BigInteger actionId;

    @Getter
    @Setter
    @Column(name = "volunteer_id")
    private BigInteger volunteerId;

    @Getter
    @Setter
    @Column(name = "status_application")
    private Boolean status;

}
