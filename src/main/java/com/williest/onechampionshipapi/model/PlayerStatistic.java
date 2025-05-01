package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlayerStatistic {
    private final UUID id = UUID.randomUUID();
    private int totalGoalsScored;
    private double totalPlayingTime;
}
