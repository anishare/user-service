package com.anishare.userservice.service;

import com.anishare.userservice.dto.AnimeDTO;
import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserRecommendationService {
    UserRecommendationsDTO findById(UUID uuid);
    Page<ResponseDTO<AnimeDTO>> findByFrom(String token, String from, Pageable pageable);
    Page<ResponseDTO<AnimeDTO>> findByTo(String token, String to, Pageable pageable);
    UserRecommendationsDTO add(UserRecommendationsDTO item);
    void changeStatus(UUID id, boolean status);
    void delete(UUID id);
}
