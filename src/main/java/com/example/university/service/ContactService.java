package com.example.university.service;

import com.example.university.dto.request.ContactRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.dto.response.ContactResponseDto;
import com.example.university.entity.ContactEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public ApiResponse add(ContactRequestDto contactRequestDto) {
        ContactEntity of = ContactEntity.of(contactRequestDto);
        ContactEntity save = contactRepository.save(of);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                ContactResponseDto.from(save)
        );
    }

    public ApiResponse update(UUID id, ContactRequestDto contactRequestDto) {
        ContactEntity byId = getById(id);
        if (contactRequestDto.getAddressRu() != null && !contactRequestDto.getAddressRu().equals("")) {
            byId.setAddressRu(contactRequestDto.getAddressRu());
        }
        if (contactRequestDto.getAddressUz() != null && !contactRequestDto.getAddressUz().equals("")) {
            byId.setAddressUz(contactRequestDto.getAddressUz());
        }
        if (contactRequestDto.getAddressEng() != null && !contactRequestDto.getAddressEng().equals("")) {
            byId.setAddressEng(contactRequestDto.getAddressEng());
        }
        if (contactRequestDto.getPhoneNumber() != null) {
            byId.setPhoneNumber(contactRequestDto.getPhoneNumber());
        }
        if (contactRequestDto.getEmail() != null) {
            byId.setEmail(contactRequestDto.getEmail());
        }
        ContactEntity save = contactRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                ContactResponseDto.from(save)
        );
    }

    public ApiResponse delete(UUID id) {
        ContactEntity byId = getById(id);
        contactRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    private ContactEntity getById(UUID id) {
        return contactRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Contact not found"));
    }

    public ApiResponse getOne() {
        for (ContactEntity contactEntity : contactRepository.findAll()) {
            return new ApiResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    ContactResponseDto.from(contactEntity)
            );
        }
        return null;
    }
}
