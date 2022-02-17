package com.anishare.userservice.repository;

import com.anishare.userservice.model.UserRecommendations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRecommendationsRepository extends JpaRepository<UserRecommendations, UUID> {
    Page<UserRecommendations> findByFromUser(String from, Pageable pageable);
    Page<UserRecommendations> findByToUser(String to, Pageable pageable);
    boolean existsByFromUserAndToUserAndItemID(String fromUser, String toUser, UUID itemID);

    @Modifying
    @Query("UPDATE UserRecommendations SET isFinished = :status where id = :id")
    void updateStatusByID(@Param("id") UUID id, @Param("status") boolean status);
}
