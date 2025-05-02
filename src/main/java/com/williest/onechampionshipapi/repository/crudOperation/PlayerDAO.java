package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.PlayerMapper;
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
public class PlayerDAO implements EntityDAO<Player> {
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;
    private final PlayerMapper playerMapper;

    public List<Player> findAll(String playerName, String clubName) {
        List<Player> players = new ArrayList<>();
        sqlRequest = "SELECT * FROM player INNER JOIN club ON player.club_id = club.club_id ";
        if(playerName != null && clubName == null){
            sqlRequest += " WHERE player_name ILIKE '%" + playerName + "%'";
        }
        if(clubName != null && playerName == null){
            sqlRequest += " WHERE club_name ILIKE '%" + clubName + "%'";
        }
        if(playerName != null && clubName != null){
            sqlRequest += " WHERE player_name ILIKE '%" + playerName + "%' AND club_name ILIKE '%" + clubName + "%'";
        }

        try(Connection dbConnection = dataSourceDB.getConnection()){
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);
            ResultSet rs = select.executeQuery();
            while(rs.next()){
                Player player = this.playerMapper.apply(rs);
                players.add(player);
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYERS : " + e.getMessage());
        }

        return players;
    }

    @Override
    public Player findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player save(Player entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Player> saveAll(List<Player> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player update(Player entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
