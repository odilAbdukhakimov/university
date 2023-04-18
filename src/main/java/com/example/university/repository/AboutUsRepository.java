package com.example.university.repository;

import com.example.university.entity.AboutUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AboutUsRepository extends JpaRepository<AboutUsEntity, UUID> {
    Optional<AboutUsEntity> findByCategory_Id(UUID id);
}
