package com.example.webappvolunteer.dto;

import com.example.webappvolunteer.enums.Role;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
public class VolunteerDto {

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
    private String mail;

    @Getter
    @Setter
    private String passwords;

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

    @Getter
    @Setter
    private Role role;

}
