package com.williest.onechampionshipapi.restController.restEntity;

import com.williest.onechampionshipapi.model.ClubStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubRestWithStatistics {
    private final String id;
    private String name;
    private String creationYear;
    private String acronym;
    private String stadiumName;
    private CoachRest coach;
    private ClubStatistics clubStatistics;
}
