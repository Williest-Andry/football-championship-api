package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.ClubStatistics;
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
            return new ClubStatistics(
                    (UUID) rs.getObject("club_statistic_id"),
                    rs.getInt("scored_goals"),
                    0,
                    rs.getInt("conceded_goals"),
                    rs.getInt("difference_goals"),
                    rs.getInt("clean_sheets")
            );
        } catch (SQLException e) {
            throw new ServerException("ERROR IN CLUB STATISTICS MAPPER : " + e.getMessage());
        }
    }
}
