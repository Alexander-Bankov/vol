package com.example.webappvolunteer.dto;

import com.example.webappvolunteer.entity.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class EventInfoDto {
    @Getter
    @Setter
    private String eventName;

    @Getter
    @Setter
    private String actionName;

    @Getter
    @Setter
    private String place;

    @Getter
    @Setter
    private LocalDateTime startTime;

    @Getter
    @Setter
    private LocalDateTime endTime;

    @Getter
    @Setter
    private Integer volunteerCount;

    @Getter
    @Setter
    private Integer maxVolunteerCount;


}
