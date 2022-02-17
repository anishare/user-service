package com.anishare.userservice.mapper;

import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;
import com.anishare.userservice.model.UserRecommendations;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRecommendationMapper {
    UserRecommendationsDTO entityToDTO(UserRecommendations userRecommendations);
    UserRecommendations dtoToEntity(UserRecommendationsDTO userRecommendationsDTO);
    List<UserRecommendationsDTO> entityListToDTOList(List<UserRecommendations> userRecommendations);
    ResponseDTO entityToResponseDTO(UserRecommendationsDTO dto);
}
