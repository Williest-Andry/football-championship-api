package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Ranking {
    private final UUID id = UUID.randomUUID();
    private int rank;
    private int points;
}
