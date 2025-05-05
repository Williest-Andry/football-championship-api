package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.repository.crudOperation.ClubDAO;
import com.williest.onechampionshipapi.repository.crudOperation.CoachDAO;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerDAO;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubService implements EntityService<Club> {
    private final ClubDAO clubDAO;
    private final CoachDAO coachDAO;
    private final PlayerDAO playerDAO;
    private final PlayerService playerService;

    public List<Club> getAllClubs() {
        return this.clubDAO.findAll();
    }

    @Override
    public Club getById(String id) {
        UUID validClubId = IdVerification.validUUID(id);
        Club foundClub = this.clubDAO.findById(validClubId);
        if (foundClub == null) {
            throw new ClientException("The club with id : " + id + " does not exist");
        }

        foundClub.setPlayers(this.playerDAO.findAllByClubId(validClubId));

        return foundClub;
    }

    @Override
    public Club save(Club club) {
        Club savedClub = this.clubDAO.save(club);

        this.playerDAO.saveAll(club.getPlayers());
        List<Player> clubPlayers = this.playerDAO.findAllByClubId(savedClub.getId());
        savedClub.setPlayers(clubPlayers);

        return savedClub;
    }

    @Override
    public List<Club> saveAll(List<Club> clubs) {
        clubs.forEach(club ->{
            Coach coach = this.coachDAO.findByName(club.getCoach().getName());
            if(coach == null){
                throw new ClientException("The coach with name : " + club.getCoach().getName() + " does not exist");
            }
            club.setCoach(coach);
        });
        return this.clubDAO.saveAll(clubs);
    }

    public List<Player> updateClubPlayers(String clubId, List<Player> players){
        Club foundClub = this.getById(clubId);

        players.forEach(player -> {
            Player foundPlayer = this.playerService.getById(player.getId().toString());

            UUID foundPlayerClubId = foundPlayer.getClub().getId();
            if(foundPlayerClubId != null && !foundPlayerClubId.toString().equals(clubId)){
                throw new ClientException("The player with id : " + player.getId() +
                        " has already a club with id : " + foundPlayer.getClub().getId());
            }
        });

        foundClub.getPlayers().forEach(exisingPlayer -> {
            exisingPlayer.setClub(null);
        });

        foundClub.addPlayers(players);

        Club savedClub = this.save(foundClub);
        return savedClub.getPlayers();
    }

    @Override
    public Club delete(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
