package com.anishare.userservice.controller;

import com.anishare.userservice.dto.AnimeDTO;
import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.service.UserRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;

    @Autowired
    public UserRecommendationController(UserRecommendationService userRecommendationService) {
        this.userRecommendationService = userRecommendationService;
    }

    @GetMapping
    public ResponseEntity<Page<ResponseDTO<AnimeDTO>>> getItems(JwtAuthenticationToken jwt, Pageable pageable) {
        return ResponseEntity.ok(userRecommendationService.findByFrom(jwt.getToken().getTokenValue(), jwt.getName(), pageable));
    }
}
