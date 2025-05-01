package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.Country;
import com.williest.onechampionshipapi.model.enumeration.LeagueName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class League {
    private final UUID id = UUID.randomUUID();
    private LeagueName name;
    private Country country;
    private List<Club> clubs;
    private List<Match> allMatches;
    private Season season;
    private List<Ranking> generalRanking;
}
