package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.League;
import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.repository.crudOperation.LeagueDAO;
import com.williest.onechampionshipapi.repository.crudOperation.SeasonDAO;
import com.williest.onechampionshipapi.restController.createRestEntity.UpdateSeasonStatus;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeasonService implements EntityService<Season> {
    private final SeasonDAO seasonDAO;
    private final LeagueDAO leagueDAO;

    public List<Season> getAllSeasons() {
        return this.seasonDAO.findAll();
    }

    public Season getByYear(String seasonYear){
        Season foundSeason = this.seasonDAO.findByYear(seasonYear);
        if(foundSeason == null){
            throw new NotFoundException("Season with year : " + seasonYear + " not found");
        }

        return foundSeason;
    }

    @Override
    public Season getById(String id) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Season save(Season season) {
        return this.seasonDAO.save(season);
    }

    @Override
    public List<Season> saveAll(List<Season> seasons) {
        seasons.forEach(season -> {
            Season foundSeason = this.seasonDAO.findByYear(String.valueOf(season.getYear()));
            if (foundSeason != null) {
                throw new ClientException("Season with year : " + season.getYear() + " already exists");
            }

            season.updateStatus(SeasonStatus.NOT_STARTED);

            League theOneLeague = this.leagueDAO.findTheOneLeague();
            season.setLeague(theOneLeague);
        });
        return seasonDAO.saveAll(seasons);
    }

    public Season updateStatus(String seasonYear, UpdateSeasonStatus status) {
        Season foundSeason = this.seasonDAO.findByYear(seasonYear);
        if(foundSeason == null){
            throw new NotFoundException("Season with year : " + seasonYear + " not found");
        }

        foundSeason.updateStatus(status.getStatus());

        return this.save(foundSeason);
    }

    @Override
    public Season delete(Season entity) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
