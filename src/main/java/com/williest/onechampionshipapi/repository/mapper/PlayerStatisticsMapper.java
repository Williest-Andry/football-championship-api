package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.model.PlayerPlayingTime;
import com.williest.onechampionshipapi.model.PlayerStatistics;
import com.williest.onechampionshipapi.model.enumeration.DurationUnit;
import com.williest.onechampionshipapi.service.exception.ServerException;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
public class PlayerStatisticsMapper implements Function<ResultSet, PlayerStatistics> {
    @Override
    public PlayerStatistics apply(ResultSet rs) {
        try{
            double totalTimeMinute = rs.getDouble("playing_time_minute");
            double totalTimeSecond = totalTimeMinute * 60;
            PlayerPlayingTime totalTimePlaying = new PlayerPlayingTime(
                    totalTimeSecond,
                    DurationUnit.SECOND
            );

            int totalGoals;
            try{
                totalGoals = rs.getInt("total_goals");
            } catch(Exception e){
                totalGoals = 0;
            }

            Match match = Match.builder().id((UUID) rs.getObject("id_match")).build();

            PlayerStatistics playerStatistics = new PlayerStatistics(
                    (UUID) rs.getObject("player_statistic_id"),
                    totalGoals,
                    totalTimePlaying
            );
            playerStatistics.setMatch(match);

            return playerStatistics;
        } catch(SQLException e){
            throw new ServerException("ERROR IN PLAYER STATISTICS MAPPER : " + e.getMessage());
        }
    }
}
