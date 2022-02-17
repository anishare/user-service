package com.anishare.userservice.service;

import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;

import java.util.List;
import java.util.UUID;

public interface UserRecommendationService {
    UserRecommendationsDTO findById(UUID uuid);
    List<ResponseDTO> findByFrom(String token, String from);
    List<ResponseDTO> findByTo(String token, String to);
    UserRecommendationsDTO add(UserRecommendationsDTO item);
    void changeStatus(UUID id, boolean status);
    void delete(UUID id);
}
