package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.ClubStatistics;
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
    private final MatchDAO matchDAO;

    @Override
    public ClubStatistics apply(ResultSet rs) {
        try {
            return new ClubStatistics(
                    (UUID) rs.getObject("club_statistic_id"),
                    rs.getInt("scored_goals"),
                    0,
                    0,
                    0,
                    0
            );
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
