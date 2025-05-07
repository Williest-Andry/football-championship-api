package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.League;
import com.williest.onechampionshipapi.model.enumeration.Country;
import com.williest.onechampionshipapi.model.enumeration.LeagueName;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LeagueDAO {
    private final DataSourceDB dataSource;
    private final String sqlRequest = "SELECT * FROM league";

    public League findTheOneLeague() {
        League league = null;

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery()){
                if(rs.next()){
                    league = League.builder()
                            .id((UUID) rs.getObject("league_id"))
                            .name(LeagueName.valueOf(rs.getString("league_name")))
                            .country(Country.valueOf(rs.getString("country"))).build();
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND THE ONE LEAGUE : " + e.getMessage());
        }

        return league;
    }
}
