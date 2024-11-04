package com.example.webappvolunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "volunteer")
@AllArgsConstructor
@NoArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "man_id")
    private BigInteger volunteerId;

    @Getter
    @Setter
    @Column(name = "e_mail")
    private String eMail;

    @Getter
    @Setter
    @Column(name = "passwords")
    private String passwords;

    @Getter
    @Setter
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "second_name")
    private String secondName;

    @Getter
    @Setter
    @Column(name = "sex")
    private String sex;

    @Getter
    @Setter
    @Column(name = "experience")
    private String experience;

    @Getter
    @Setter
    @Column(name = "languages")
    private String language;

    @Getter
    @Setter
    @Column(name = "brief_info")
    private String briefInfo;
}
