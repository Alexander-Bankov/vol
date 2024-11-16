package com.example.webappvolunteer.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String passwords;
}