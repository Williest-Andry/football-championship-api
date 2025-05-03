package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.PlayerPlayingTime;
import com.williest.onechampionshipapi.model.PlayerStatistics;
import com.williest.onechampionshipapi.model.enumeration.DurationUnit;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayerStatisticDAO implements EntityDAO<PlayerStatistics> {
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;

    public PlayerStatistics findByPlayerNoGoal(UUID playerId, int seasonYear){
        PlayerStatistics playerStatistics = null;

        sqlRequest = "SELECT player_statistic_id, SUM(playing_time_minute) AS total_time_playing" +
                " FROM player_statistic " +
                " INNER JOIN player ON player.player_id = player_statistic.player_id" +
                " INNER JOIN season ON season.season_id = player_statistic.season_id " +
                " WHERE player_statistic.player_id = ? AND season.year = ?::varchar " +
                " GROUP BY player_statistic_id ;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, playerId);
            select.setInt(2, seasonYear);
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    double totalTimeMinute = rs.getDouble("total_time_playing");
                    double totalTimeSecond = totalTimeMinute * 60;
                    PlayerPlayingTime totalTimePlaying = new PlayerPlayingTime(
                            totalTimeSecond,
                            DurationUnit.SECOND
                    );

                    playerStatistics = new PlayerStatistics(
                            (UUID) rs.getObject("player_statistic_id"),
                            0,
                            totalTimePlaying
                    );
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS NO GOAL BY PLAYER_ID AND SEASON YEAR : " + e.getMessage());
        }

        return playerStatistics;
    }

    public PlayerStatistics findByPlayerIdAndSeasonYear(UUID playerId, int seasonYear) {
        PlayerStatistics playerStatistics = null;

        sqlRequest = "SELECT player_statistic_id, SUM(scored_goals) AS total_goals, SUM(playing_time_minute) AS total_time_playing" +
                " FROM player_statistic " +
                " INNER JOIN player ON player.player_id = player_statistic.player_id" +
                " INNER JOIN goal ON goal.player_id = player.player_id " +
                " INNER JOIN season ON season.season_id = player_statistic.season_id " +
                " WHERE player_statistic.player_id = ? AND season.year = ?::varchar AND own_goal = false " +
                "GROUP BY player_statistic_id ;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, playerId);
            select.setInt(2, seasonYear);
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    double totalTimeMinute = rs.getDouble("total_time_playing");
                    double totalTimeSecond = totalTimeMinute * 60;
                    PlayerPlayingTime totalTimePlaying = new PlayerPlayingTime(
                            totalTimeSecond,
                            DurationUnit.SECOND
                    );

                    playerStatistics = new PlayerStatistics(
                            (UUID) rs.getObject("player_statistic_id"),
                            rs.getInt("total_goals"),
                            totalTimePlaying
                    );
                } else{
                    return this.findByPlayerNoGoal(playerId, seasonYear);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS  BY PLAYER_ID AND SEASON YEAR : " + e.getMessage());
        }

        return playerStatistics;
    }

    @Override
    public PlayerStatistics findById(UUID id) {
        return null;
    }

    @Override
    public PlayerStatistics save(PlayerStatistics entity) {
        return null;
    }

    @Override
    public List<PlayerStatistics> saveAll(List<PlayerStatistics> entities) {
        return List.of();
    }

    @Override
    public PlayerStatistics update(PlayerStatistics entity) {
        return null;
    }

    @Override
    public PlayerStatistics deleteById(UUID id) {
        return null;
    }
}
