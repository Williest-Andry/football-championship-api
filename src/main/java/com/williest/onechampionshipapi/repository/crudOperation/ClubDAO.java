package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.ClubMapper;
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
public class ClubDAO implements EntityDAO<Club>{
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;
    private final ClubMapper clubMapper;

    public List<Club> findAll(){
        List<Club> clubs = new ArrayList<>();
        sqlRequest = "SELECT club_id, coach_id, club_name, creation_year, acronym, stadium_name FROM club" +
                " ORDER BY club_name ASC;";

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    Club club = this.clubMapper.apply(rs);
                    clubs.add(club);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL CLUBS : " + e.getMessage());
        }

        return clubs;
    }

    @Override
    public Club findById(UUID id) {
        Club foundClub = null;
        sqlRequest = "SELECT club_id, coach_id, club_name, creation_year, acronym, stadium_name FROM club WHERE club_id = ?;";

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery()){
                if(rs.next()){
                    foundClub = this.clubMapper.apply(rs);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL CLUBS : " + e.getMessage());
        }

        return foundClub;
    }

    @Override
    public Club save(Club club) {
        if(this.findById(club.getId()) != null){
            return this.update(club);
        }
        UUID savedClubId = null;
        sqlRequest = "INSERT INTO club(club_id, coach_id, club_name, creation_year, acronym, stadium_name) " +
                "VALUES(?,?,?,?,?,?) RETURNING club_id;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, club.getId());
            insert.setObject(2, club.getCoach().getId());
            insert.setString(3, club.getName());
            insert.setString(4, club.getYearCreation());
            insert.setString(5, club.getAcronym());
            insert.setString(6, club.getStadium());
            try(ResultSet rs = insert.executeQuery()){
                if(rs.next()){
                    savedClubId = ((UUID) rs.getObject("club_id"));
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE CLUB : " + e.getMessage());
        }

        return this.findById(savedClubId);
    }

    @Override
    public List<Club> saveAll(List<Club> clubs) {
        return clubs.stream().map(this::save).toList();
    }

    @Override
    public Club update(Club club) {
        sqlRequest = "UPDATE club SET coach_id = ?, club_name = ?, creation_year = ?, acronym = ?, stadium_name = ?" +
                " WHERE club_id = ?;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, club.getCoach().getId());
            select.setString(2, club.getName());
            select.setString(3, club.getYearCreation());
            select.setString(4, club.getAcronym());
            select.setString(5, club.getStadium());
            select.setObject(6, club.getId());
            select.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN UPDATE CLUB : " + e.getMessage());
        }

        return this.findById(club.getId());
    }

    @Override
    public Club deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
