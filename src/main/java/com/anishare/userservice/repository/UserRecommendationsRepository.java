package com.anishare.userservice.repository;

import com.anishare.userservice.model.UserRecommendations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRecommendationsRepository extends JpaRepository<UserRecommendations, UUID> {
    Page<UserRecommendations> findByFromUser(String from, Pageable pageable);
    Page<UserRecommendations> findByToUser(String to, Pageable pageable);
}
