package com.williest.onechampionshipapi.restController.restEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClubRest {
    private final String id;
    private String name;
    private String creationYear;
    private String acronym;
    private String stadiumName;
    private CoachRest coach;

    @JsonIgnore
    public boolean isValid(){
        return id != null && name != null && acronym != null && stadiumName != null && coach != null && coach.isValid();
    }
}
