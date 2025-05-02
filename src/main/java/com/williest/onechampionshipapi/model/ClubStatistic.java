package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ClubStatistic {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private int totalRankingPoints;
    private int totalGoals;
    private int totalConcededGoals;
    private int goalDifference;
    private int totalCleanSheets;
}
