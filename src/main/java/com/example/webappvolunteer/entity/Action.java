package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "action")
@AllArgsConstructor
@NoArgsConstructor
public class Action implements Serializable {
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
    private LocalDate actionEnd;

    @Getter
    @Setter
    @Column(name = "status")
    private String status;

}
