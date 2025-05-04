package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.CoachMapper;
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
public class CoachDAO implements EntityDAO<Coach>{
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;
    private final CoachMapper coachMapper;

    @Override
    public Coach findById(UUID id) {
        Coach foundCoach = null;

        sqlRequest = "SELECT * FROM coach WHERE coach_id = ?";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery()){
                if(rs.next()){
                    foundCoach = this.coachMapper.apply(rs);
                }
            }

        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND COACH BY ID : " + e.getMessage());
        }

        return foundCoach;
    }

    @Override
    public Coach save(Coach entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Coach> saveAll(List<Coach> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Coach update(Coach entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Coach deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
