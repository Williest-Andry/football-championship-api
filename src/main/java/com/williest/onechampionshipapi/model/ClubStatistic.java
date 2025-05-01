package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubStatistic {
    private String id;
    private int totalRankingPoints;
    private int totalGoals;
    private int totalConcededGoals;
    private int goalDifference;
    private int totalCleanSheets;
}
