package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.Country;
import com.williest.onechampionshipapi.model.enumeration.LeagueName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class League {
    private String id;
    private LeagueName name;
    private Country country;
    private List<Club> clubs;
    private Club champion;
    private List<Match> allMatches;
    private Season season;
    private List<Ranking> generalRanking;
}
