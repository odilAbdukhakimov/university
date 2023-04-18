package com.example.university.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ContactRequestDto {
    private String addressRu;
    private String addressUz;
    private String addressEng;
    private List<String> phoneNumber;
    private List<String> email;
}
