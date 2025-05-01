package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ranking {
    private String id;
    private int rank;
    private int points;
    private Club club;
}
