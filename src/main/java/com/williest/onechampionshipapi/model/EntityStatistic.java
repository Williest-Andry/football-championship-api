package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public abstract class EntityStatistic {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private int scoredGoals;
}
