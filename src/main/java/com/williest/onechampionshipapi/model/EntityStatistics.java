package com.williest.onechampionshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.UUID;

@Data
public abstract class EntityStatistics {
    @JsonIgnore
    private UUID id;
    private int scoredGoals;
    @JsonIgnore
    private Match match;

    public EntityStatistics(UUID id, int scoredGoals) {
        this.id = id != null ? id : UUID.randomUUID();
        this.scoredGoals = scoredGoals;
    }
}
