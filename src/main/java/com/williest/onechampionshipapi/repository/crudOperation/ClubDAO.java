package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubDAO implements EntityDAO<Club>{
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;

    @Override
    public Club findById(UUID id) {
        return null;
    }

    @Override
    public Club save(Club entity) {
        return null;
    }

    @Override
    public List<Club> saveAll(List<Club> entities) {
        return List.of();
    }

    @Override
    public Club update(Club entity) {
        return null;
    }

    @Override
    public Club deleteById(UUID id) {
        return null;
    }
}
