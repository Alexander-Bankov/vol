package com.example.webappvolunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionFilterDto {

    private String status;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}
