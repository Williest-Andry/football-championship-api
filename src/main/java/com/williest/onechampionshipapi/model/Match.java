package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Match {
    private String id;
    private LocalDateTime matchDate;
    private Club homeTeam;
    private Club awayTeam;
    private League league;
}
