package com.example.university.dto.response;

import com.example.university.entity.ContactEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ContactResponseDto {
    private UUID id;
    @JsonProperty("address_ru")
    private String addressRu;
    @JsonProperty("address_uz")
    private String addressUz;
    @JsonProperty("address_eng")
    private String addressEng;
    @JsonProperty("phone_number")
    private List<String> phoneNumber;
    private List<String> email;

    public static ContactResponseDto from(ContactEntity entity) {
        return ContactResponseDto.builder()
                .id(entity.getId())
                .addressRu(entity.getAddressRu())
                .addressUz(entity.getAddressUz())
                .addressEng(entity.getAddressEng())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .build();
    }
}
