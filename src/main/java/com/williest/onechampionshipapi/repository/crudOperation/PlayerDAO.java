package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayerDAO implements EntityDAO<Player> {
    @Override
    public Player findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Player> findAll() {
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
