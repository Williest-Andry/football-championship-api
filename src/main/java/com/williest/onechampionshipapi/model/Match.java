package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
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
    private MatchClub clubPlayingHome;
    private MatchClub clubPlayingAway;
    private String stadium;
    private LocalDateTime matchDateTime;
    private MatchStatus actualStatus;
    private Season season;
    private League league;
    private List<PlayerStatistics> playerStatistics;
    private List<ClubStatistics> clubStatistics;
}
