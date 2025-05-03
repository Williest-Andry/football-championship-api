package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.PlayerStatistic;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayerStatisticDAO implements EntityDAO<PlayerStatistic> {
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;

    public List<PlayerStatistic> findByPlayerIdAndSeasonYear() {
        List<PlayerStatistic> playerStatistics = new ArrayList<>();
        try(Connection dbConnection = dataSourceDB.getConnection()){

        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYER STATISTICS  BY PLAYER_ID AND SEASON YEAR : " + e.getMessage());
        }

        return playerStatistics;
    }

    @Override
    public PlayerStatistic findById(UUID id) {
        return null;
    }

    @Override
    public PlayerStatistic save(PlayerStatistic entity) {
        return null;
    }

    @Override
    public List<PlayerStatistic> saveAll(List<PlayerStatistic> entities) {
        return List.of();
    }

    @Override
    public PlayerStatistic update(PlayerStatistic entity) {
        return null;
    }

    @Override
    public PlayerStatistic deleteById(UUID id) {
        return null;
    }
}
