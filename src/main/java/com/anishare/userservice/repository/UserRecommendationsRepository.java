package com.anishare.userservice.repository;

import com.anishare.userservice.model.UserRecommendations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRecommendationsRepository extends JpaRepository<UserRecommendations, UUID> {
    List<UserRecommendations> findByFromUser(String from);
    List<UserRecommendations> findByToUser(String to);
    boolean existsByFromUserAndToUserAndItemID(String fromUser, String toUser, UUID itemID);

    @Modifying
    @Query("UPDATE UserRecommendations SET isFinished = :status where id = :id")
    void updateStatusByID(@Param("id") UUID id, @Param("status") boolean status);
}
