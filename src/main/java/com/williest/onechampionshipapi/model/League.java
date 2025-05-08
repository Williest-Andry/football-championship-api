package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.Country;
import com.williest.onechampionshipapi.model.enumeration.LeagueName;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class League {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private LeagueName name;
    private Country country;
    private List<Club> clubs;
    private List<Match> allMatches;
    private List<Season> season;
}
