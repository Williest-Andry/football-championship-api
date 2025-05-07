package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.model.MatchClub;
import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.repository.crudOperation.MatchDAO;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService implements EntityService<Match> {
    private final MatchDAO matchDAO;
    private final ClubService clubService;
    private final SeasonService seasonService;

    public List<Match> matchMakerForOneSeason(String seasonYear) {
        if(seasonYear.length() != 4){
            throw new ClientException("The season year should be 4 characters");
        }
        try{
            Integer.parseInt(seasonYear);
        } catch(Exception e){
            throw new ClientException("The season year should be 4 digits");
        }

        Season foundSeason = this.seasonService.getByYear(seasonYear);
        if(foundSeason.getStatus() != SeasonStatus.STARTED){
            throw new ClientException("The status of the season with year : " + seasonYear + " is not STARTED " +
                    "but : " + foundSeason.getStatus());
        }

        List<Club> allClubs = this.clubService.getAllClubs();

        List<Match> allMatches = this.matchDAO.findAllBySeasonYear(seasonYear);


        if(allMatches.size() >= (allClubs.size()) * (allClubs.size() - 1)){
            throw new ClientException("All matches for the season with year : " + seasonYear + " are already generated");
        }

        List<Match> generatedMatches = new ArrayList<>();

        for(int i = 0; i < allClubs.size(); i++){
            for(int j = 0; j < allClubs.size(); j++){
                MatchClub clubHome = new MatchClub();
                clubHome.setClub(allClubs.get(i));
                clubHome.setClubScore(null);

                MatchClub clubAway = null;
                if (allClubs.get(i) != allClubs.get(j)) {
                    clubAway = new MatchClub();
                    clubAway.setClub(allClubs.get(j));
                    clubAway.setClubScore(null);

                    Match newMatch = Match.builder()
                            .league(allClubs.get(i).getLeague())
                            .clubPlayingHome(clubHome)
                            .clubPlayingAway(clubAway)
                            .stadium(clubHome.getClub().getStadium())
                            .matchDateTime(null)
                            .actualStatus(MatchStatus.NOT_STARTED).build();

                    Match generatedNewMatch = this.matchDAO.save(newMatch);
                    generatedMatches.add(generatedNewMatch);
                }
            }
        }

        return generatedMatches;
    }

    @Override
    public Match getById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Match save(Match entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Match> saveAll(List<Match> matches) {
        return matches.stream().map(this::save).toList();
    }

    @Override
    public Match delete(Match entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
