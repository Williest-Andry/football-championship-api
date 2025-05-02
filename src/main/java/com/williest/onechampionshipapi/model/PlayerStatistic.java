package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class PlayerStatistic {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private int totalGoalsScored;
    private double totalPlayingTime;
}
