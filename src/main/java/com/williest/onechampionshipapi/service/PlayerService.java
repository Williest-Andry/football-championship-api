package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements EntityService<Player> {
    private final PlayerDAO playerDAO;

    public List<Player> getAllPlayers(String name, int ageMinimum, int ageMaximum, String clubName) {
        throw new UnsupportedOperationException("Not supported yet.");
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
