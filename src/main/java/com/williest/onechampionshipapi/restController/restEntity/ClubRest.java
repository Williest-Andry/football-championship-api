package com.williest.onechampionshipapi.restController.restEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClubRest {
    private final UUID id;
    private String name;
    @JsonIgnore
    private LocalDate creationYear;
    private String acronym;
    private String stadiumName;
    private CoachRest coach;

    public int getYearCreation(){
        return creationYear.getYear();
    }
}
