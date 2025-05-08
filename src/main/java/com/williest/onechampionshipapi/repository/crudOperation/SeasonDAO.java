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
        Season season = null;
        sqlRequest = "SELECT * FROM season WHERE season_id = ?;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()){
                while (rs.next()){
                    season = this.seasonMapper.apply(rs);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND SEASON ID : " + e.getMessage());
        }

        return season;
    }

    public Season findByYear(String seasonYear){
        Season season = null;
        sqlRequest = "SELECT * FROM season WHERE year = ?;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setString(1, seasonYear);
            try (ResultSet rs = select.executeQuery()){
                while (rs.next()){
                    season = this.seasonMapper.apply(rs);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND SEASON YEAR : " + e.getMessage());
        }

        return season;
    }

    @Override
    public Season save(Season season) {
        if(this.findById(season.getId()) != null){
            return this.update(season);
        }
        UUID savedSeasonId = null;
        sqlRequest = "INSERT INTO season (season_id, year, alias, status, league_id) " +
                "VALUES (?,?,?,?::season_status,?) RETURNING season_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, season.getId());
            select.setString(2, String.valueOf(season.getYear()));
            select.setString(3, season.getValidAlias());
            select.setString(4, season.getStatus().toString());
            select.setObject(5, season.getLeague().getId());
            try(ResultSet rs = select.executeQuery()){
                if(rs.next()){
                    savedSeasonId = (UUID) rs.getObject("season_id");
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE SEASON : " + e.getMessage());
        }

        return this.findById(savedSeasonId);
    }

    @Override
    public List<Season> saveAll(List<Season> seasons) {
        return seasons.stream().map(this::save).toList();
    }

    @Override
    public Season update(Season season) {
        sqlRequest = "UPDATE season SET status = ?::season_status WHERE season_id = ?;";
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setString(1, season.getStatus().toString());
            select.setObject(2, season.getId());
            select.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN UPDATE SEASON : " + e.getMessage());
        }

        return this.findById(season.getId());
    }

    @Override
    public Season deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
