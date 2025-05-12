package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.ClubScore;
import com.williest.onechampionshipapi.model.Scorer;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.ClubScoreMapper;
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
public class ClubScoreDAO implements EntityDAO<ClubScore>{
    private final DataSourceDB dataSource;
    private String sqlRequest;
    private final ClubScoreMapper clubScoreMapper;

    public List<ClubScore> findByMatchIdAndClubId(UUID clubId, UUID matchId) {
        List<ClubScore> clubScores = new ArrayList<>();
        sqlRequest = "SELECT *, COUNT(goal_id) AS total_goals FROM goal WHERE club_id = ? AND match_id = ? " +
                "GROUP BY goal_id, club_id, player_id, minute_of_goal, own_goal, match_id," +
                " club_statistic_id, player_statistic_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            select.setObject(2, matchId);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    ClubScore clubScore = this.clubScoreMapper.apply(rs);
                    clubScores.add(clubScore);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND CLUB SCORE BY CLUB ID AND MATCH ID : " + e.getMessage());
        }

        return clubScores;
    }

    @Override
    public ClubScore findById(UUID id) {
        ClubScore foundClubScore = null;
        sqlRequest = "SELECT *, COUNT(goal_id) AS total_goals  FROM goal WHERE goal_id = ? " +
                "GROUP BY goal_id, club_id, player_id, minute_of_goal, own_goal, match_id, " +
                "club_statistic_id, player_statistic_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    foundClubScore = this.clubScoreMapper.apply(rs);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND CLUB SCORE BY ID : " + e.getMessage());
        }

        return foundClubScore;
    }

    public ClubScore saveGoalInMatch(UUID matchId, Scorer scorer, ClubScore clubScore) {
        if(this.findById(clubScore.getId()) != null){
            return clubScore;
        }

        sqlRequest = "INSERT INTO goal (goal_id, club_id, player_id, minute_of_goal, own_goal, match_id, " +
                "club_statistic_id, player_statistic_id) VALUES (?,?,?,?,?,?,?,?);";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, clubScore.getId());
            insert.setObject(2, clubScore.getClub().getId());
            insert.setObject(3, scorer.getPlayer().getId());
            insert.setInt(4, scorer.getMinuteOfGoal());
            insert.setBoolean(5, scorer.getOwnGoal());
            insert.setObject(6, matchId);
            insert.setObject(7, clubScore.getClub().getClubStatistics().getFirst().getId());
            insert.setObject(8, scorer.getPlayer().getPlayerStatistics().getFirst().getId());
            insert.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE CLUB SCORE : " + e.getMessage());
        }

        return this.findById(clubScore.getId());
    }

    @Override
    public ClubScore save(ClubScore clubScore){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ClubScore> saveAll(List<ClubScore> clubScores) {
        return clubScores.stream().map(this::save).toList();
    }

    @Override
    public ClubScore update(ClubScore entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubScore deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
