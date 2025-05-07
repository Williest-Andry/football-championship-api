package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.restController.restEntity.MatchClubRest;
import com.williest.onechampionshipapi.restController.restEntity.MatchRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MatchRestMapper implements Function<Match, MatchRest> {
    private final MatchClubRestMapper matchClubRestMapper;

    @Override
    public MatchRest apply(Match match) {
        MatchClubRest clubHome = this.matchClubRestMapper.apply(match.getClubPlayingHome());
        MatchClubRest clubAway = this.matchClubRestMapper.apply(match.getClubPlayingAway());

       return new MatchRest(
                match.getId(),
                clubHome,
                clubAway,
                match.getStadium(),
                match.getMatchDateTime(),
                match.getActualStatus()
        );
    }
}
