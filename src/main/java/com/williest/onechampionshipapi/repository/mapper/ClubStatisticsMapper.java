package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.repository.crudOperation.MatchDAO;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubStatisticsMapper implements Function<ResultSet, ClubStatistics> {

    @Override
    public ClubStatistics apply(ResultSet rs) {
        try {
            Match match = Match.builder().id((UUID) rs.getObject("id_match")).build();

            int scoredGoals = 0;
            try {
                scoredGoals = rs.getInt("scored_goals");
            } catch (SQLException e) {
                scoredGoals = 0;
            }
            ClubStatistics clubStatistics = new ClubStatistics(
                    (UUID) rs.getObject("club_statistic_id"),
                    scoredGoals,
                    0,
                    0,
                    0,
                    0
            );
            clubStatistics.setMatch(match);

            return clubStatistics;
        } catch (SQLException e) {
            throw new ServerException("ERROR IN CLUB STATISTICS MAPPER : " + e.getMessage());
        }
    }

    public ClubStatistics applyWithAllStats(ResultSet rs, int concededGoals,
                                            int matchesWhenConcededGoals,
                                            int allMatchesOfTheClub) {
        try {
            ClubStatistics clubStatistics = this.apply(rs);

            clubStatistics.setConcededGoals(concededGoals);

            clubStatistics.setDifferenceGoals(clubStatistics.getScoredGoals() - concededGoals);

            clubStatistics.setCleanSheetNumber(allMatchesOfTheClub - matchesWhenConcededGoals);

            return clubStatistics;
        } catch (Exception e) {
            throw new ServerException("ERROR IN CLUB STATISTICS MAPPER WITH ALL STATS : " + e.getMessage());
        }
    }
}
