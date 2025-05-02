package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerDAO;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements EntityService<Player> {
    private final PlayerDAO playerDAO;

    public List<Player> getAllPlayers(String playerName, Integer ageMinimum, Integer ageMaximum, String clubName) {
        if(playerName != null && playerName.isEmpty()) {
            throw new ClientException("Player name can't be empty");
        }
//        if(ageMinimum < 0 || ageMaximum < 0) {
//            throw new ClientException("Age minimum or maximum must be greater than 0");
//        }
//        if(ageMinimum > ageMaximum) {
//            throw new ClientException("Age maximum must be greater than age minimum");
//        }
        if(clubName != null && clubName.isEmpty()) {
            throw new ClientException("Club name can't be empty");
        }
        return this.playerDAO.findAll(playerName, clubName);
    }

    @Override
    public Player getById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player save(Player entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player delete(Player entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
