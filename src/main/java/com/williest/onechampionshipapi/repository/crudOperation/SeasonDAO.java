package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.repository.mapper.SeasonMapper;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SeasonDAO implements EntityDAO<Season> {
    private final DataSource dataSource;
    private String sqlRequest;
    private final SeasonMapper seasonMapper;

    public List<Season> findAll(){
        List<Season> seasons = new ArrayList<>();
        sqlRequest = "SELECT * FROM season";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try (ResultSet rs = select.executeQuery()){
                while (rs.next()){
                    Season season = this.seasonMapper.apply(rs);
                    seasons.add(season);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL SEASONS : " + e.getMessage());
        }

        return seasons;
    }

    @Override
    public Season findById(UUID id) {
        return null;
    }

    @Override
    public Season save(Season entity) {
        return null;
    }

    @Override
    public List<Season> saveAll(List<Season> entities) {
        return List.of();
    }

    @Override
    public Season update(Season entity) {
        return null;
    }

    @Override
    public Season deleteById(UUID id) {
        return null;
    }
}
