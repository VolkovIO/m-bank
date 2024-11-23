package com.example.mbank.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserContactRequest {
    private Long UserId;
    private Set<String> addPhones;
    private Set<String> removePhones;
    private Set<String> addEmails;
    private Set<String> removeEmails;
}
