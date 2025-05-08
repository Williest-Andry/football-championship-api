package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.*;
import com.williest.onechampionshipapi.repository.crudOperation.*;
import com.williest.onechampionshipapi.service.ClubStatisticsComparator.CleanSheetsComparator;
import com.williest.onechampionshipapi.service.ClubStatisticsComparator.CompareList;
import com.williest.onechampionshipapi.service.ClubStatisticsComparator.DifferenceGoalsComparator;
import com.williest.onechampionshipapi.service.ClubStatisticsComparator.RankingComparator;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.typeVerification.IdVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClubService implements EntityService<Club> {
    private final ClubDAO clubDAO;
    private final CoachDAO coachDAO;
    private final PlayerDAO playerDAO;
    private final PlayerService playerService;
    private final SeasonDAO seasonDAO;
    private final ClubStatisticsDAO clubStatisticsDAO;
    private final LeagueDAO leagueDAO;

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
        League league = this.leagueDAO.findTheOneLeague();
        club.setLeague(league);

        Club savedClub = this.clubDAO.save(club);

        this.playerDAO.saveAll(club.getPlayers());
        List<Player> clubPlayers = this.playerDAO.findAllByClubId(savedClub.getId());
        savedClub.setPlayers(clubPlayers);


        return savedClub;
    }

    @Override
    public List<Club> saveAll(List<Club> clubs) {
        League league = this.leagueDAO.findTheOneLeague();

        clubs.forEach(club ->{
            Coach coach = this.coachDAO.findByName(club.getCoach().getName());
            if(coach == null){
                throw new ClientException("The coach with name : " + club.getCoach().getName() + " does not exist");
            }
            club.setCoach(coach);
            club.setLeague(league);
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

    public List<Player> addClubPlayers(String clubId, List<Player> players){
        Club foundClub = this.getById(clubId);
        List<Player> existingPlayers = foundClub.getPlayers();

        players.forEach(player -> {
            Player foundPlayer = null;
            try {
                foundPlayer = this.playerService.getById(player.getId().toString());
            } catch (Exception e) {
                foundPlayer = this.playerDAO.save(player);
            }

            UUID foundPlayerClubId = foundPlayer.getClub().getId();
            if(foundPlayerClubId != null && !foundPlayerClubId.toString().equals(clubId)){
                throw new ClientException("The player with id : " + player.getId() +
                        " has already a club with id : " + foundPlayer.getClub().getId());
            }
        });

        List<Player> noUpdatePlayers = new ArrayList<>();

        existingPlayers.forEach(player -> {
            Player inThisClubPlayer = players.stream()
                    .filter(p -> p.getId().toString().equals(player.getId().toString())).findFirst().orElse(null);
            if(inThisClubPlayer != null){
                noUpdatePlayers.add(inThisClubPlayer);
            }
        });

        List<Player> playersToAdd = new ArrayList<>(players);
        playersToAdd.removeAll(noUpdatePlayers);

        foundClub.addPlayers(playersToAdd);

        Club savedClub = this.save(foundClub);
        return savedClub.getPlayers();
    }

    public List<Club> getAllClubsStatisticsBySeasonYear(String seasonYear, boolean hasToBeClassified){
        if(seasonYear.length() != 4){
            throw new ClientException("The season year should be 4 characters");
        }
        try{
            Integer.parseInt(seasonYear);
        } catch(Exception e){
            throw new ClientException("The season year should be 4 digits");
        }
        Season foundSeason = this.seasonDAO.findByYear(seasonYear);
        if(foundSeason == null){
            throw new ClientException("The season with year : " + seasonYear + " does not exist");
        }

        List<Club> clubs = this.clubDAO.findAll();
        clubs.forEach(club -> {
            club.setClubStatistics(this.clubStatisticsDAO.findAllByClubIdAndSeasonYear(club.getId(), seasonYear));
        });

        if(hasToBeClassified){
            RankingComparator rankingComparator = new RankingComparator();
            DifferenceGoalsComparator differenceGoalsComparator = new DifferenceGoalsComparator();
            CleanSheetsComparator cleanSheetsComparator = new CleanSheetsComparator();

            clubs.sort(rankingComparator);

            List<Integer> clubsRankingPoints = clubs.stream()
                    .map(club -> club.getGeneralClubStatistics().getRankingPoints()).toList();
            boolean hasDuplicatesWithRanking = CompareList.hasDuplicates(clubsRankingPoints);
            if(hasDuplicatesWithRanking){
                clubs.sort(differenceGoalsComparator);

                List<Integer> clubsDifferenceGoals = clubs.stream()
                        .map(club -> club.getGeneralClubStatistics().getDifferenceGoals()).toList();
                boolean hasDuplicatesWithDifference = CompareList.hasDuplicates(clubsDifferenceGoals);

                if(hasDuplicatesWithDifference){
                    clubs.sort(cleanSheetsComparator);
                }
            }
        }

        return clubs;
    }

    @Override
    public Club delete(Club entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
