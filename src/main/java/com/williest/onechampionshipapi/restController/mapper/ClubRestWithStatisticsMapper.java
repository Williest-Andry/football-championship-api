package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.restController.restEntity.ClubRestWithStatistics;
import com.williest.onechampionshipapi.restController.restEntity.CoachRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubRestWithStatisticsMapper implements Function<Club, ClubRestWithStatistics> {
    private final CoachRestMapper coachRestMapper;

    @Override
    public ClubRestWithStatistics apply(Club club) {
        CoachRest coach = this.coachRestMapper.apply(club.getCoach());

        return new ClubRestWithStatistics(
                club.getId().toString(),
                club.getName(),
                club.getYearCreation(),
                club.getAcronym(),
                club.getStadium(),
                coach,
                club.getGeneralClubStatistics()
        );
    }
}
