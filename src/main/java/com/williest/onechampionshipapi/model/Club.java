package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Club {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private String name;
    private String creationYear;
    private String acronym;
    private String stadiumName;
    private League league;
    private List<Player> players;
    private Coach coach;
    private List<ClubStatistic> clubStatistics;
    private Ranking ranking;
}
