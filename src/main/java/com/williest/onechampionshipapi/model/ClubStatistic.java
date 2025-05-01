package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ClubStatistic {
    private final UUID id = UUID.randomUUID();
    private int totalRankingPoints;
    private int totalGoals;
    private int totalConcededGoals;
    private int goalDifference;
    private int totalCleanSheets;
}
