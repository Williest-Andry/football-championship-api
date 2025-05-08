package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.ClubScore;
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
        sqlRequest = "SELECT * FROM goal WHERE club_id = ? AND match_id = ? " +
                "GROUP BY goal_id, club_id, player_id, minute_of_goal, own_goal, match_id;";

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubScore save(ClubScore entity) {
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
