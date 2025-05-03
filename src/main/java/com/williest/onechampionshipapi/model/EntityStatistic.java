package com.williest.onechampionshipapi.model;

import lombok.Data;
import java.util.UUID;

@Data
public abstract class EntityStatistic {
    private UUID id;
    private int scoredGoals;

    public EntityStatistic(UUID id, int scoredGoals) {
        this.id = id != null ? id : UUID.randomUUID();
        this.scoredGoals = scoredGoals;
    }
}
