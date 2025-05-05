package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubStatisticsDAO implements EntityDAO<ClubStatistics> {
    private final DataSourceDB dataSource;
    private String slqRequest;

    public List<ClubStatistics> findAllBySeasonYear(String seasonYear) {

    }

    @Override
    public ClubStatistics findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics save(ClubStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ClubStatistics> saveAll(List<ClubStatistics> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics update(ClubStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
