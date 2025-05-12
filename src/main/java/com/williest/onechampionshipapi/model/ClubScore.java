package com.williest.onechampionshipapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ClubScore {
    private UUID id;
    private Club club;
    private int score;
    private List<Scorer> scorers;

    public void addScorer(Scorer scorer) {
        scorers.add(scorer);
    }
}
