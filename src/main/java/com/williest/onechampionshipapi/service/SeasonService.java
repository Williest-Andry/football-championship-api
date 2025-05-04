package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.repository.crudOperation.SeasonDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeasonService implements EntityService<Season> {
    private final SeasonDAO seasonDAO;

    public List<Season> getAllSeasons() {
        return this.seasonDAO.findAll();
    }

    @Override
    public Season getById(UUID id) {
        return null;
    }

    @Override
    public Season save(Season season) {
        return this.seasonDAO.save(season);
    }

    @Override
    public List<Season> saveAll(List<Season> seasons) {
        seasons.forEach(season -> season.setStatus(SeasonStatus.NOT_STARTED));
        return seasonDAO.saveAll(seasons);
    }

    @Override
    public Season delete(Season entity) {
        return null;
    }
}
