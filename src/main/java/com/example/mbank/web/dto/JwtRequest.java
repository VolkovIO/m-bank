package com.example.mbank.web.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
