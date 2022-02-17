package com.anishare.userservice.controller;

import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.service.UserRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;

    @Autowired
    public UserRecommendationController(UserRecommendationService userRecommendationService) {
        this.userRecommendationService = userRecommendationService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getItems(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(userRecommendationService.findByFrom(jwt.getToken().getTokenValue(), jwt.getName()));
    }

    @GetMapping("/getTo")
    public ResponseEntity<List<ResponseDTO>> getToItems(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(userRecommendationService.findByTo(jwt.getToken().getTokenValue(), jwt.getName()));
    }
}
