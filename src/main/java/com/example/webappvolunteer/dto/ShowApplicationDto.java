package com.example.webappvolunteer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowApplicationDto {

    private String actionName;
    private String eventName;
    private String mail;
    private String status;

}
