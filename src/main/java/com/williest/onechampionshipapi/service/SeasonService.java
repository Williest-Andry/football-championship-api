package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.repository.crudOperation.SeasonDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonDAO seasonDAO;

    public List<Season> getAllSeasons() {
        return this.seasonDAO.findAll();
    }
}
