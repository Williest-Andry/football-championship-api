package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerStatistic {
    private String id;
    private int totalGoalsScored;
    private double totalPlayingTime;
}
