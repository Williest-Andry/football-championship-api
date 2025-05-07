package com.williest.onechampionshipapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubScore {
    private int score;
    private List<Scorer> scorers;

    public void addScorer(Scorer scorer) {
        scorers.add(scorer);
    }
}
