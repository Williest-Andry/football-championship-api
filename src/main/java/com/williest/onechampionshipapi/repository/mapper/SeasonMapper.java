package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.League;
import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.service.exception.ServerException;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
public class SeasonMapper implements Function<ResultSet, Season> {
    @Override
    public Season apply(ResultSet rs) {
        try {
            League league = League.builder().id((UUID) rs.getObject("league_id")).build();

            return Season.builder()
                    .id((UUID) rs.getObject("season_id"))
                    .year(Integer.parseInt(rs.getString("year")))
                    .alias(rs.getString("alias"))
                    .status(SeasonStatus.valueOf(rs.getString("status")))
                    .league(league)
                    .clubStatistics(null).playerStatistics(null).build();
        } catch (SQLException e) {
            throw new ServerException("ERROR IN SEASON MAPPER : " + e.getMessage());
        }
    }
}
