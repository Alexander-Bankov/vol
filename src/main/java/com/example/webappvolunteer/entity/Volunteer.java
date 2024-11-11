package com.example.webappvolunteer.entity;

import com.example.webappvolunteer.enums.Role;
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
    @Column(name = "volunteer_id")
    private BigInteger volunteerId;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;



    @Getter
    @Setter
    @Column(name = "second_name")
    private String secondName;

    @Getter
    @Setter
    @Column(name = "e_mail")
    private String mail;

    @Getter
    @Setter
    @Column(name = "volunteer_password")
    private String passwords;

    @Getter
    @Setter
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Getter
    @Setter
    @Column(name = "addres")
    private String address;


    @Getter
    @Setter
    @Column(name = "phone")
    private String phone;

    @Getter
    @Setter
    @Column(name = "volunteer_info")
    private String volunteerInfo;


    @Getter
    @Setter
    @Column(name = "languages")
    private String language;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    private Role role;


}
