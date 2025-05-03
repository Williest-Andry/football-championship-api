package com.williest.onechampionshipapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PlayerStatistics extends EntityStatistics {
    private PlayerPlayingTime playingTime;

    public PlayerStatistics(UUID id, int scoredGoals, PlayerPlayingTime playingTime) {
        super(id, scoredGoals);
        this.playingTime = playingTime;
    }
}
