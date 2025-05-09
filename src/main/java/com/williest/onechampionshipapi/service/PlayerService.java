package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.*;
import com.williest.onechampionshipapi.model.enumeration.DurationUnit;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.repository.crudOperation.ClubDAO;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerDAO;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerStatisticsDAO;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.typeVerification.IdVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements EntityService<Player> {
    private final PlayerDAO playerDAO;
    private final PlayerStatisticsDAO playerStatisticsDAO;
    private final ClubDAO clubDAO;
    private final SeasonService seasonService;

    public List<Player> getAllPlayers(String playerName, Integer ageMinimum, Integer ageMaximum, String clubName) {
        if(playerName != null && playerName.isEmpty()) {
            throw new ClientException("Player name can't be empty");
        }
        if(clubName != null && clubName.isEmpty()) {
            throw new ClientException("Club name can't be empty");
        }
        if((ageMinimum != null && ageMaximum !=null ) && (ageMinimum <= 0 || ageMaximum <= 0)) {
            throw new ClientException("Age minimum or maximum must be greater than 0");
        }
        if( (ageMinimum != null && ageMaximum !=null ) && ageMinimum > ageMaximum) {
            throw new ClientException("Age maximum must be greater than age minimum");
        }

        List<Player> foundPlayers = this.playerDAO.findAll(playerName, clubName);
        List<Player> foundPlayersWithoutClub = this.playerDAO.findAllWithoutClub(playerName);
        foundPlayersWithoutClub.forEach(player -> {
            player.setClub(null);
            if(clubName == null){
                foundPlayers.add(player);
            }
        });

        if(ageMinimum != null && ageMaximum == null) {
            return foundPlayers.stream().filter(player -> player.getAge() >= ageMinimum).toList();
        }
        else if(ageMinimum == null && ageMaximum != null) {
            return foundPlayers.stream().filter(player -> player.getAge() <= ageMaximum).toList();
        }
        else if(ageMinimum != null && ageMaximum != null) {
            return foundPlayers.stream()
                    .filter(player -> player.getAge() >= ageMinimum && player.getAge() <= ageMaximum).toList();
        }
        return foundPlayers;
    }

    @Override
    public Player getById(String id) {
        UUID validPlayerId = IdVerification.validUUID(id);
        Player foundPlayer = this.playerDAO.findById(validPlayerId);
        if(foundPlayer == null){
            throw new ClientException("Player with id : " + id + " not found");
        }

        return foundPlayer;
    }

    @Override
    public Player save(Player player) {
        return this.playerDAO.save(player);
    }

    @Override
    public List<Player> saveAll(List<Player> entities) {
        return entities.stream().map(this::save).toList();
    }

    public PlayerStatistics getPlayerStatistics(String playerId, String seasonYear){
        Player foundPlayer = this.getById(playerId);

        Season foundSeason = this.seasonService.getByYear(seasonYear);
        if(foundSeason.getStatus() == SeasonStatus.NOT_STARTED){
            throw new ClientException("The status of the season with year : "+ seasonYear + " is NOT_STARTED ");
        }

        PlayerStatistics foundPlayerStatistics = this.playerStatisticsDAO
                .findByPlayerIdAndSeasonYear(foundPlayer.getId(), seasonYear);

        return foundPlayerStatistics != null ? foundPlayerStatistics :
                new PlayerStatistics(
                        null,
                        0,
                        new PlayerPlayingTime(
                                0.0,
                                DurationUnit.SECOND
                        )
        );
    }

    public List<Player> getClubPlayersByClubId(String clubId){
        UUID validClubId = IdVerification.validUUID(clubId);
        Club foundClub = this.clubDAO.findById(validClubId);
        if(foundClub == null){
            throw new ClientException("The club with id : " + clubId + " does not exist");
        }

        return this.playerDAO.findAllByClubId(validClubId);
    }

    @Override
    public Player delete(Player entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
