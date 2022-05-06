package com.anishare.userservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseDTO {

    private UUID id;
    private String fromUser;
    private String toUser;
    private LocalDateTime dateCreated;
    private AnimeDTO item;
    private boolean isAnime;
    private boolean isFinished;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AnimeDTO getItem() {
        return item;
    }

    public void setItem(AnimeDTO item) {
        this.item = item;
    }

    public boolean isAnime() {
        return isAnime;
    }

    public void setAnime(boolean anime) {
        isAnime = anime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
