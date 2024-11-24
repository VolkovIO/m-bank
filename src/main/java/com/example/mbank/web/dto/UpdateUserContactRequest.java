package com.example.mbank.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserContactRequest {
    private Set<String> addPhones;
    private Set<String> removePhones;
    private Set<String> addEmails;
    private Set<String> removeEmails;
}
