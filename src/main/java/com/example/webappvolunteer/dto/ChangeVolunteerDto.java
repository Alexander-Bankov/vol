package com.example.webappvolunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
public class ChangeVolunteerDto {

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String secondName;

    @Getter
    @Setter
    private LocalDate birthdate;

    @Getter
    @Setter
    private String address;


    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String volunteerInfo;


    @Getter
    @Setter
    private String language;
}
