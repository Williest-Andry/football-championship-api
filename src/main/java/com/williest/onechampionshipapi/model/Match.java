package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Match {
    private final UUID id = UUID.randomUUID();
    private LocalDateTime matchDate;
    private Club homeTeam;
    private Club awayTeam;
    private League league;
}
