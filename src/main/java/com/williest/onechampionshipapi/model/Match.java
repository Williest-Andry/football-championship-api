package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Match {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private LocalDateTime matchDate;
    private Club homeTeam;
    private Club awayTeam;
    private League league;
    private List<PlayerStatistic> playerStatistics;
    private List<ClubStatistic> clubStatistics;
}
