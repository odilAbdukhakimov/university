package com.example.university.entity;

import com.example.university.dto.request.ContactRequestDto;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactEntity extends BaseEntity {
    private String addressRu;
    private String addressUz;
    private String addressEng;
    @ElementCollection
    private List<String> phoneNumber;
    @ElementCollection
    private List<String> email;

    public static ContactEntity of(ContactRequestDto dto) {
        return ContactEntity.builder()
                .addressRu(dto.getAddressRu())
                .addressUz(dto.getAddressUz())
                .addressEng(dto.getAddressEng())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}
