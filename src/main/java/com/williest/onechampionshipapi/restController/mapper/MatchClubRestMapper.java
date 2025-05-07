package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.MatchClub;
import com.williest.onechampionshipapi.restController.restEntity.MatchClubRest;
import com.williest.onechampionshipapi.restController.restEntity.ScorerRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MatchClubRestMapper implements Function<MatchClub, MatchClubRest> {
    private final ScorerRestMapper scorerRestMapper;

    @Override
    public MatchClubRest apply(MatchClub matchClub) {
        List<ScorerRest> scorersRestList = matchClub.getClubScore().getScorers().stream().map(this.scorerRestMapper).toList();

        return new MatchClubRest(
                matchClub.getClub().getId(),
                matchClub.getClub().getName(),
                matchClub.getClub().getAcronym(),
                matchClub.getClubScore().getScore(),
                scorersRestList
        );
    }
}
