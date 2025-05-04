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
        sqlRequest = "SELECT club_id, coach_id, club_name, creation_year, acronym, stadium_name FROM club;";

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club save(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Club> saveAll(List<Club> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club update(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
