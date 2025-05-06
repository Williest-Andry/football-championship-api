package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.restController.restEntity.CoachRest;
import com.williest.onechampionshipapi.service.IdVerification;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubRestMapper implements Function<Club, ClubRest> {
    private final CoachRestMapper coachRestMapper;

    @Override
    public ClubRest apply(Club club) {
        CoachRest coachRest = this.coachRestMapper.apply(club.getCoach());

        return new ClubRest(
                club.getId().toString(),
                club.getName(),
                club.getYearCreation(),
                club.getAcronym(),
                club.getStadium(),
                coachRest
        );
    }

    public Club toModel(ClubRest clubRest) {
        if(!clubRest.isValid()){
            throw new ClientException("The club is not valid");
        }
        Coach coach = this.coachRestMapper.toModel(clubRest.getCoach());
        UUID validId = IdVerification.validOrGenerateUUID(clubRest.getId());

        return Club.builder()
                .id(validId)
                .name(clubRest.getName())
                .stadium(clubRest.getStadiumName())
                .acronym(clubRest.getAcronym())
                .yearCreation(clubRest.getCreationYear())
                .coach(coach)
                .players(null).clubStatistics(null).league(null).build();
    }
}
