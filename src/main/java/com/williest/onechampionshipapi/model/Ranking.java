package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Ranking {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private int rank;
    private int points;
}
