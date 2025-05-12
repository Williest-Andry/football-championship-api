package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.PlayerStatistics;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.PlayerStatisticsMapper;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayerStatisticsDAO implements EntityDAO<PlayerStatistics> {
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;
    private final PlayerStatisticsMapper playerStatisticsMapper;

    public PlayerStatistics findByPlayerNoGoal(UUID playerId, String seasonYear){
        PlayerStatistics playerStatistics = null;

        sqlRequest = "SELECT player_statistic_id, SUM(playing_time_minute) AS playing_time_minute," +
                "match_id AS id_match" +
                " FROM player_statistic " +
                " INNER JOIN player ON player.player_id = player_statistic.player_id" +
                " INNER JOIN season ON season.season_id = player_statistic.season_id " +
                " WHERE player_statistic.player_id = ? AND season.year = ?::varchar " +
                " GROUP BY player_statistic_id, match_id;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, playerId);
            select.setInt(2, Integer.parseInt(seasonYear));
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    playerStatistics = this.playerStatisticsMapper.apply(rs);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS NO GOAL BY PLAYER_ID AND SEASON YEAR : "
                    + e.getMessage());
        }

        return playerStatistics;
    }

    public PlayerStatistics findByPlayerIdAndSeasonYear(UUID playerId, String seasonYear) {
        PlayerStatistics playerStatistics = null;

        sqlRequest = "SELECT player_statistic.player_statistic_id, COUNT(goal_id) AS total_goals, " +
                "SUM(playing_time_minute) AS playing_time_minute, player_statistic.match_id AS id_match" +
                " FROM player_statistic " +
                " INNER JOIN player ON player.player_id = player_statistic.player_id" +
                " INNER JOIN goal ON goal.player_id = player.player_id " +
                " INNER JOIN season ON season.season_id = player_statistic.season_id " +
                " WHERE player_statistic.player_id = ? AND season.year = ?::varchar AND own_goal = false " +
                "GROUP BY player_statistic.player_statistic_id ;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, playerId);
            select.setInt(2, Integer.parseInt(seasonYear));
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    playerStatistics = this.playerStatisticsMapper.apply(rs);
                } else{
                    return this.findByPlayerNoGoal(playerId, seasonYear);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS  BY PLAYER_ID AND SEASON YEAR : "
                    + e.getMessage());
        }

        return playerStatistics;
    }

    public List<PlayerStatistics> findAllByPlayerId(UUID playerId) {
        List<PlayerStatistics> playerStatistics = new ArrayList<>();

        sqlRequest = "SELECT *, match.match_id AS id_match FROM player_statistic " +
                "JOIN match ON match.match_id = player_statistic.match_id " +
                "WHERE player_id = ?";

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, playerId);
            try(ResultSet rs = select.executeQuery();){
                while(rs.next()) {
                    playerStatistics.add(this.playerStatisticsMapper.apply(rs));
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS  BY PLAYER_ID : " + e.getMessage());
        }

        return playerStatistics;
    }

    @Override
    public PlayerStatistics findById(UUID id) {
        PlayerStatistics playerStatistics = null;
        sqlRequest = "SELECT *, match_id AS id_match FROM player_statistic WHERE player_statistic_id = ?";

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    playerStatistics = this.playerStatisticsMapper.apply(rs);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND PLAYER STATISTICS BY ID : " + e.getMessage());
        }

        return playerStatistics;
    }

    @Override
    public PlayerStatistics save(PlayerStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public PlayerStatistics saveWithSeasonIdAndMatchId(PlayerStatistics playerStatistics, UUID player_id,
                                                       UUID seasonId, UUID matchId, int playingTimeMinute) {
        sqlRequest = "INSERT INTO player_statistic(player_statistic_id, player_id, season_id, " +
                "match_id, playing_time_minute)" +
                " VALUES (?,?,?,?,?)";

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, playerStatistics.getId());
            insert.setObject(2, player_id);
            insert.setObject(3, seasonId);
            insert.setObject(4, matchId);
            insert.setInt(5, playingTimeMinute);
            insert.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE PLAYER STATISTICS : " + e.getMessage());
        }

        return this.findById(playerStatistics.getId());
    }

    @Override
    public List<PlayerStatistics> saveAll(List<PlayerStatistics> entities) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public PlayerStatistics update(PlayerStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public PlayerStatistics deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
