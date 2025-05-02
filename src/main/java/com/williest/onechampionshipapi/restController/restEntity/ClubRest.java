package com.williest.onechampionshipapi.restController.restEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClubRest {
    private final UUID id;
    private String name;
    private String creationYear;
    private String acronym;
    private String stadiumName;
    private CoachRest coach;
}
