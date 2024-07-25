package com.example.vpapi.repository;

import com.example.vpapi.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
