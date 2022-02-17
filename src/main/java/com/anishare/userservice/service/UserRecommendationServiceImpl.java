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
        return processResult(token, items);
    }

    @Override
    public Page<ResponseDTO<AnimeDTO>> findByTo(String token, String to, Pageable pageable) {
        Page<UserRecommendationsDTO> items = userRecommendationsRepository.findByToUser(to, pageable).map(userRecommendationMapper::entityToDTO);
        return processResult(token, items);
    }

    @Override
    public UserRecommendationsDTO add(UserRecommendationsDTO item) {
        if (userRecommendationsRepository.existsByFromUserAndToUserAndItemID(item.getFromUser(), item.getToUser(), item.getItemID())) {
            throw new IllegalStateException("You have already recommended this");
        }
        return userRecommendationMapper.entityToDTO(userRecommendationsRepository.save(userRecommendationMapper.dtoToEntity(item)));
    }

    @Override
    public void changeStatus(UUID id, boolean status) {
        userRecommendationsRepository.updateStatusByID(id, status);
    }

    @Override
    public void delete(UUID id) {
        userRecommendationsRepository.deleteById(id);
    }

    private Page<ResponseDTO<AnimeDTO>> processResult(String token, Page<UserRecommendationsDTO> items) {
        List<UUID> list = items.stream().map(UserRecommendationsDTO::getItemID).collect(Collectors.toList());
        List<AnimeDTO> animeList = getData(list, token, AnimeDTO.class);
        return  items.map(userRecommendationsDTO -> {
            ResponseDTO<AnimeDTO> temp = userRecommendationMapper.entityToResponseDTO(userRecommendationsDTO);
            temp.setItem(animeList.stream().filter(x -> x.getId().compareTo(userRecommendationsDTO.getItemID()) == 0).findFirst().orElse(null));
            return temp;
        });
    }

    public HttpHeaders getHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return httpHeaders;
    }

    public <T> List<T> getData(List<UUID> list, String token, Class<T> tClass) {
        HttpEntity<List<UUID>> entity = new HttpEntity<>(list, getHeaders(token));
        String url = (tClass.getGenericSuperclass() instanceof AnimeDTO) ? "http://ANIME-SERVICE/anime/findAllById" :
                "http://MANGA-SERVICE/manga/findAllById";
        ResponseEntity<List<T>> exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return exchange.getBody();
    }
}
