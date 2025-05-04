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

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery();){
                while(rs.next()){
                    Player player = this.playerMapper.apply(rs);
                    players.add(player);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYERS : " + e.getMessage());
        }

        return players;
    }

    public List<Player> findAllWithoutClub(String playerName, String clubName){
        List<Player> players = new ArrayList<>();
        sqlRequest = "SELECT * FROM player WHERE club_id IS NULL;";
        if(playerName != null && clubName == null){
            sqlRequest += " WHERE player_name ILIKE '%" + playerName + "%'";
        }
        if(clubName != null && playerName == null){
            sqlRequest += " WHERE club_name ILIKE '%" + clubName + "%'";
        }
        if(playerName != null && clubName != null){
            sqlRequest += " WHERE player_name ILIKE '%" + playerName + "%' AND club_name ILIKE '%" + clubName + "%'";
        }

        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            try(ResultSet rs = select.executeQuery();){
                while(rs.next()){
                    Player player = this.playerMapper.applyWithoutClub(rs);
                    players.add(player);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND ALL PLAYERS WITHOUT CLUB : " + e.getMessage());
        }

        return players;
    }

    @Override
    public Player findById(UUID id) {
        Player foundPlayer = null;

        sqlRequest = "SELECT * FROM player WHERE player_id = ?";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()){
                    foundPlayer = this.playerMapper.applyWithoutClub(rs);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND PLAYER BY ID : " + e.getMessage());
        }

        return foundPlayer;
    }

    @Override
    public Player save(Player player) {
        if(this.findById(player.getId()) != null){
            return this.update(player);
        }

        UUID savedPlayerId = null;

        sqlRequest = "INSERT INTO player VALUES (?,?,?,?,?,?,?::player_position_in_field) RETURNING player_id;";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, player.getId());
            insert.setObject(2, null);
            insert.setString(3, player.getName());
            insert.setInt(4, player.getNumber());
            insert.setString(5, player.getNationality());
            insert.setString(6, player.getBirth_year());
            insert.setString(7, player.getPlayerPosition().toString());
            try(ResultSet rs = insert.executeQuery();){
                if(rs.next()){
                    savedPlayerId = (UUID) rs.getObject("player_id");
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE PLAYER : " + e.getMessage());
        }

        return this.findById(savedPlayerId);
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        return players.stream().map(this::save).toList();
    }

    @Override
    public Player update(Player player) {
        sqlRequest = "UPDATE player SET club_id = ?, player_name = ?, player_number = ?, player_nationality = ?," +
                    " player_birth_year = ?, player_position = ?::player_position_in_field WHERE player_id = ?";
        try(Connection dbConnection = dataSourceDB.getConnection();
            PreparedStatement update = dbConnection.prepareStatement(sqlRequest);){
            update.setObject(1, player.getClub() != null? player.getClub().getId() : null);
            update.setString(2, player.getName());
            update.setInt(3, player.getNumber());
            update.setString(4, player.getNationality());
            update.setString(5, player.getBirth_year());
            update.setString(6, player.getPlayerPosition().toString());
            update.setObject(7, player.getId());
            update.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN UPDATE PLAYER : " + e.getMessage());
        }

        return this.findById(player.getId());
    }

    @Override
    public Player deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
