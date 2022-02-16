package com.anishare.userservice.service;

import com.anishare.userservice.dto.AnimeDTO;
import com.anishare.userservice.dto.ResponseDTO;
import com.anishare.userservice.dto.UserRecommendationsDTO;
import com.anishare.userservice.mapper.UserRecommendationMapper;
import com.anishare.userservice.repository.UserRecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRecommendationServiceImpl implements UserRecommendationService {

    private final UserRecommendationsRepository userRecommendationsRepository;
    private final UserRecommendationMapper userRecommendationMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public UserRecommendationServiceImpl(UserRecommendationsRepository userRecommendationsRepository, UserRecommendationMapper userRecommendationMapper, RestTemplate restTemplate) {
        this.userRecommendationsRepository = userRecommendationsRepository;
        this.userRecommendationMapper = userRecommendationMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserRecommendationsDTO findById(UUID uuid) {
        return userRecommendationMapper.entityToDTO(userRecommendationsRepository.findById(uuid).orElse(null));
    }

    @Override
    public Page<ResponseDTO<AnimeDTO>> findByFrom(String token, String from, Pageable pageable) {
        Page<UserRecommendationsDTO> items = userRecommendationsRepository.findByFromUser(from, pageable).map(userRecommendationMapper::entityToDTO);
        List<UUID> list = items.stream().map(UserRecommendationsDTO::getItemID).collect(Collectors.toList());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity<List<UUID>> entity = new HttpEntity<>(list, httpHeaders);
        ResponseEntity<List<AnimeDTO>> exchange = restTemplate.exchange(
                "http://ANIME-SERVICE/anime/findAllById",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        List<AnimeDTO> animeList = exchange.getBody();
        return  items.map(userRecommendationsDTO -> {
            ResponseDTO<AnimeDTO> temp = userRecommendationMapper.entityToResponseDTO(userRecommendationsDTO);
            temp.setItem(animeList.stream().filter(x -> x.getId().compareTo(userRecommendationsDTO.getItemID()) == 0).findFirst().orElse(null));
            return temp;
        });
    }

    @Override
    public Page<ResponseDTO<AnimeDTO>> findByTo(String to, Pageable pageable) {
        return null;
    }

    @Override
    public UserRecommendationsDTO add(UserRecommendationsDTO item) {
        return null;
    }

    @Override
    public void changeStatus(UUID id, boolean status) {

    }

    @Override
    public void delete(UserRecommendationsDTO item) {

    }
}
