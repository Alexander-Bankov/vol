package com.example.webappvolunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFilterDto {

    private Integer maxVolCount;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;
}
