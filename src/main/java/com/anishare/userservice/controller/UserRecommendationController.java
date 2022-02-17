package com.anishare.userservice.controller;

import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;
import com.anishare.userservice.service.UserRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/")
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;

    @Autowired
    public UserRecommendationController(UserRecommendationService userRecommendationService) {
        this.userRecommendationService = userRecommendationService;
    }

    @GetMapping("/getFrom")
    public ResponseEntity<List<ResponseDTO>> getItems(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(userRecommendationService.findByFrom(jwt.getToken().getTokenValue(), jwt.getName()));
    }

    @GetMapping("/getTo")
    public ResponseEntity<List<ResponseDTO>> getToItems(JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(userRecommendationService.findByTo(jwt.getToken().getTokenValue(), jwt.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<UserRecommendationsDTO> add(@RequestBody UserRecommendationsDTO dto) {
        return ResponseEntity
                .created(URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()))
                .body(userRecommendationService.add(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRecommendationsDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userRecommendationService.findById(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userRecommendationService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
