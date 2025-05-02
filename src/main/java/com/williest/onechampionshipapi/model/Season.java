package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Season {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private LocalDate beginDate;
    private LocalDate endDate;
    private List<ClubStatistic> clubStatistics;
    private List<PlayerStatistic> playerStatistics;
}