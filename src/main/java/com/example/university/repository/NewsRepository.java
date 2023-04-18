package com.example.university.repository;

import com.example.university.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    List<NewsEntity> findByOrderByDateTimeDesc();
}
