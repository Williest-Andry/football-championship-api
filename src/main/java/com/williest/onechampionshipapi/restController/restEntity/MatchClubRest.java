package com.williest.onechampionshipapi.restController.restEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MatchClubRest {
    private UUID id;
    private String name;
    private String acronym;
    private int score;
    private List<ScorerRest> scorers;
}
