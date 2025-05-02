package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PlayerStatistic extends EntityStatistic{
    private double playingTime;
}
