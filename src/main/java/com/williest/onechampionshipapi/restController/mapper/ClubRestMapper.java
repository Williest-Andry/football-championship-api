package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.restController.restEntity.CoachRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubRestMapper implements Function<Club, ClubRest> {
    private final CoachRestMapper coachRestMapper;

    @Override
    public ClubRest apply(Club club) {
        CoachRest coachRest = this.coachRestMapper.apply(club.getCoach());

        return new ClubRest(
                club.getId(),
                club.getName(),
                club.getYearCreation(),
                club.getAcronym(),
                club.getStadium(),
                coachRest
        );
    }
}
