package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
@Entity
@Table(name = "guide_action")
@AllArgsConstructor
@NoArgsConstructor
public class GuideAction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_action")
    private BigInteger actionId;

    @Getter
    @Setter
    @Column(name = "name_action")
    private String actionName;

}
