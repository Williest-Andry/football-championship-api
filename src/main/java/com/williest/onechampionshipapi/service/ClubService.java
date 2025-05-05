package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.repository.crudOperation.ClubDAO;
import com.williest.onechampionshipapi.repository.crudOperation.CoachDAO;
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

    public List<Club> getAllClubs() {
        return this.clubDAO.findAll();
    }

    @Override
    public Club getById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club save(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public Club delete(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
