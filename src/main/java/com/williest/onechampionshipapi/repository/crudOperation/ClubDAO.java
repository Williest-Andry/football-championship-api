package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubDAO implements EntityDAO<Club>{
    private final DataSourceDB dataSourceDB;
    private String sqlRequest;

    @Override
    public Club findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club save(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Club> saveAll(List<Club> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club update(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
