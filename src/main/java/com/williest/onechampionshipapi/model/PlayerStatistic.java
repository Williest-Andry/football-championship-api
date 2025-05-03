package com.williest.onechampionshipapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PlayerStatistic extends EntityStatistic{
    private PlayerPlayingTime playingTime;

    public PlayerStatistic(UUID id, int scoredGoals, PlayerPlayingTime playingTime) {
        super(id, scoredGoals);
        this.playingTime = playingTime;
    }
}
