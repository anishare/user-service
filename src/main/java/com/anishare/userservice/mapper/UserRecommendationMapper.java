package com.anishare.userservice.mapper;

import com.anishare.userservice.dto.AnimeDTO;
import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;
import com.anishare.userservice.model.UserRecommendations;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRecommendationMapper {
    UserRecommendationsDTO entityToDTO(UserRecommendations userRecommendations);
    UserRecommendations dtoToEntity(UserRecommendationsDTO userRecommendationsDTO);
    ResponseDTO<AnimeDTO> entityToResponseDTO(UserRecommendationsDTO dto);
}
