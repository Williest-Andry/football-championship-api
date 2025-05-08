package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.MatchMapper;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                "INNER JOIN season ON season.season_id = match.season_id " +
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

    public List<Match> findAllBySeasonYearWithConditions(String seasonYear, String matchStatus,
                                                         String clubPlayingName,
                                                         String matchAfter, String matchBeforeOrEquals){
        List<Match> foundMatches = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        sqlRequest = "SELECT * FROM match " +
                "INNER JOIN season ON season.season_id = match.season_id ";

        conditions.add(" year = '" + seasonYear+"'");

        if(clubPlayingName != null){
            sqlRequest += " INNER JOIN club ON club.club_id = match.club_playing_home " +
                    "OR club.club_id = match.club_playing_away ";
            conditions.add(" club_name ILIKE '%"+clubPlayingName+"%' ");
        }
        if(matchStatus != null){
            conditions.add(" actual_status = '" + matchStatus+"'::match_status");
        }

        if(matchAfter != null){
            conditions.add(" match_date_time > '"+Timestamp.valueOf(matchAfter)+"'");
        }
        if(matchBeforeOrEquals != null){
            conditions.add(" match_date_time <= '"+Timestamp.valueOf(matchBeforeOrEquals)+"'");
        }

        sqlRequest += " WHERE " + String.join(" AND ", conditions);

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    Match foundMatch = this.matchMapper.apply(rs);
                    foundMatches.add(foundMatch);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL MATCHES BY SEASON YEAR WITH CONDITIONS : " + e.getMessage());
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
        if(this.findById(match.getId()) != null){
            return this.update(match);
        }

        UUID savedMatchId = null;
        sqlRequest = "INSERT INTO match (match_id, league_id, match_date_time, club_playing_home, club_playing_away, stadium, actual_status, season_id) " +
                " VALUES (?,?,?,?,?,?,?::match_status,?) RETURNING match_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, match.getId());
            insert.setObject(2, match.getLeague().getId());
            insert.setTimestamp(3, match.getMatchDateTime() != null ? Timestamp.valueOf(match.getMatchDateTime())
                    : null);
            insert.setObject(4, match.getClubPlayingHome().getClub().getId());
            insert.setObject(5, match.getClubPlayingAway().getClub().getId());
            insert.setString(6, match.getClubPlayingHome().getClub().getStadium());
            insert.setString(7, match.getActualStatus().toString());
            insert.setObject(8, match.getSeason().getId());
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
    public Match update(Match match) {
        sqlRequest = "UPDATE match SET actual_status = ?::match_status WHERE match_id = ?";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement update = dbConnection.prepareStatement(sqlRequest);){
            update.setString(1, match.getActualStatus().toString());
            update.setObject(2, match.getId());
            update.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN UPDATE MATCH : " + e.getMessage());
        }

        return this.findById(match.getId());
    }

    @Override
    public Match deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
