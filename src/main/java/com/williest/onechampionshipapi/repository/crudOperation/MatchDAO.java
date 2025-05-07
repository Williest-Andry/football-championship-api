package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.ClubMapper;
import com.williest.onechampionshipapi.repository.mapper.MatchMapper;
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
public class MatchDAO implements EntityDAO<Match>{
    private final DataSourceDB dataSource;
    private String sqlRequest;
    private final MatchMapper matchMapper;

    public List<Match> findAll(){
        List<Match> foundMatches = new ArrayList<>();
        sqlRequest = "SELECT * FROM match;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    Match foundMatch = this.matchMapper.apply(rs);
                    foundMatches.add(foundMatch);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL MATCHES : " + e.getMessage());
        }

        return foundMatches;
    }

    public List<Match> findAllBySeasonYear(String seasonYear){
        List<Match> foundMatches = new ArrayList<>();
        sqlRequest = "SELECT * FROM match " +
                " INNER JOIN league ON league.league_id = match.league_id " +
                "INNER JOIN league_season ON league.league_id = league_season.league_id " +
                "INNER JOIN season ON season.season_id = league_season.season_id " +
                "WHERE year = ?;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setString(1, seasonYear);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    Match foundMatch = this.matchMapper.apply(rs);
                    foundMatches.add(foundMatch);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL MATCHES BY SEASON YEAR : " + e.getMessage());
        }

        return foundMatches;
    }

    @Override
    public Match findById(UUID id) {
        Match foundMatch = null;
        sqlRequest = "SELECT * FROM match WHERE match_id = ?";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery()){
                if(rs.next()){
                    foundMatch = this.matchMapper.apply(rs);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND MATCH BY ID : " + e.getMessage());
        }

        return foundMatch;
    }

    @Override
    public Match save(Match match) {
        UUID savedMatchId = null;
        sqlRequest = "INSERT INTO match VALUES (?,?,?,?,?,?,?::match_status) RETURNING match_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, match.getId());
            insert.setObject(2, match.getLeague().getId());
            insert.setTimestamp(3, null);
            insert.setObject(4, match.getClubPlayingHome().getClub().getId());
            insert.setObject(5, match.getClubPlayingAway().getClub().getId());
            insert.setString(6, match.getClubPlayingHome().getClub().getName());
            insert.setString(7, match.getActualStatus().toString());
            try(ResultSet rs = insert.executeQuery()){
                if(rs.next()){
                    savedMatchId = (UUID) rs.getObject("match_id");
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN SAVE MATCH : " + e.getMessage());
        }

        return this.findById(savedMatchId);
    }

    @Override
    public List<Match> saveAll(List<Match> matchs) {
        return matchs.stream().map(this::save).toList();
    }

    @Override
    public Match update(Match entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Match deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
