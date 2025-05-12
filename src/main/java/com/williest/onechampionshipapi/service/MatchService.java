package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.*;
import com.williest.onechampionshipapi.model.enumeration.DurationUnit;
import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.repository.crudOperation.ClubScoreDAO;
import com.williest.onechampionshipapi.repository.crudOperation.ClubStatisticsDAO;
import com.williest.onechampionshipapi.repository.crudOperation.MatchDAO;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerStatisticsDAO;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.typeVerification.IdVerification;
import com.williest.onechampionshipapi.service.typeVerification.StringVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService implements EntityService<Match> {
    private final MatchDAO matchDAO;
    private final ClubService clubService;
    private final SeasonService seasonService;
    private final PlayerService playerService;
    private final ClubScoreDAO clubScoreDAO;
    private final ClubStatisticsDAO clubStatisticsDAO;
    private final PlayerStatisticsDAO playerStatisticsDAO;

    public List<Match> matchMakerForOneSeason(String seasonYear) {
        this.seasonService.isValidSeasonYear(seasonYear);

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
                clubHome.setClubScore(new ClubScore(null, null, 0, null));
                ClubScore clubHomeScore = clubHome.getClubScore();
                clubHomeScore.setClub(allClubs.get(i));

                MatchClub clubAway = null;
                if (allClubs.get(i) != allClubs.get(j)) {
                    clubAway = new MatchClub();
                    clubAway.setClubScore(new ClubScore(null, null, 0, null));
                    ClubScore clubAwayScore = clubAway.getClubScore();
                    clubAwayScore.setClub(allClubs.get(j));

                    Match newMatch = Match.builder()
                            .league(allClubs.get(i).getLeague())
                            .clubPlayingHome(clubHome)
                            .clubPlayingAway(clubAway)
                            .stadium(clubHome.getClubScore().getClub().getStadium())
                            .matchDateTime(null)
                            .actualStatus(MatchStatus.NOT_STARTED)
                            .season(foundSeason).build();

                    Match generatedNewMatch = this.matchDAO.save(newMatch);
                    generatedMatches.add(generatedNewMatch);
                }
            }
        }

        return generatedMatches;
    }

    public List<Match> getAllMatchForOneSeason(String seasonYear, String matchStatus,
                                               String clubPlayingName,
                                               String matchAfter, String matchBeforeOrEquals){
        this.seasonService.isValidSeasonYear(seasonYear);

        Season foundSeason = this.seasonService.getByYear(seasonYear);
        if(foundSeason.getStatus() == SeasonStatus.NOT_STARTED){
            throw new ClientException("Can't find all matches of the season with year : " +seasonYear+ " because its status "+
                    "is : " + foundSeason.getStatus());
        }

        if(matchStatus != null){
            try{
                MatchStatus.valueOf(matchStatus);
            } catch(Exception e){
                throw new ClientException("Match status must be one of the following values: "
                        + Arrays.toString(MatchStatus.values()));
            }
        }

        if(matchAfter != null &&  !StringVerification.isValidDateString(matchAfter)){
            throw new ClientException("The date of match after is invalid");
        }

        if(matchBeforeOrEquals != null &&  !StringVerification.isValidDateString(matchBeforeOrEquals)){
            throw new ClientException("the date of match before or equals is invalid");
        }

        return this.matchDAO.findAllBySeasonYearWithConditions(seasonYear, matchStatus,
                clubPlayingName, matchAfter, matchBeforeOrEquals);
    }

    public Match updateMatchStatus(String matchId, MatchStatus matchStatus) {
        Match foundMatch = this.getById(matchId);

        foundMatch.updateActualStatus(matchStatus);

        return this.matchDAO.save(foundMatch);
    }

    public Match addGoalInMatches(String matchId, List<Scorer> scorers){
        Match foundMatch = this.getById(matchId);
        if(foundMatch.getActualStatus() != MatchStatus.STARTED){
            throw new ClientException("The actual status of the match with id : " + matchId + " is not STARTED");
        }

        MatchClub clubHome = foundMatch.getClubPlayingHome();
        MatchClub clubAway = foundMatch.getClubPlayingAway();

        UUID clubHomeId = clubHome.getClubScore().getClub().getId();
        UUID clubAwayId = clubAway.getClubScore().getClub().getId();

        scorers.forEach(scorer -> {
            UUID scorerId = scorer.getPlayer().getId();
            scorer.setPlayer(this.playerService.getById(scorerId.toString()));

            UUID scorerClubId = scorer.getPlayer().getClub().getId();
            this.clubService.getById(scorerClubId.toString());

            clubHome.getClubScore().setId(UUID.randomUUID());
            clubAway.getClubScore().setId(UUID.randomUUID());

            List<PlayerStatistics> playerStatistic = new ArrayList<>(scorer.getPlayer().getPlayerStatistics()
                    .stream().filter(playerStatistics -> playerStatistics.getMatch().getId().equals(foundMatch.getId()))
                    .toList());
            if(playerStatistic.isEmpty()){
                playerStatistic.add(this.playerStatisticsDAO.saveWithSeasonIdAndMatchId(
                        new PlayerStatistics(
                                UUID.randomUUID(),
                                0,
                                new PlayerPlayingTime(90.0, DurationUnit.SECOND)
                        ),
                        scorerId, foundMatch.getSeason().getId(), foundMatch.getId(), 90)
                );
            }
            scorer.getPlayer().setPlayerStatistics(playerStatistic);

            if(scorerClubId.equals(clubHomeId)){
                System.out.println("haha");
                Player foundPlayer = clubHome.getClubScore().getClub().getPlayers().stream()
                        .filter(player -> player.getId().equals(scorerId)).toList().getFirst();
                if(foundPlayer == null){
                    scorer.setOwnGoal(true);
                }
                List<ClubStatistics> clubStatistic = new ArrayList<>(clubHome.getClubScore().getClub().getClubStatistics()
                        .stream().filter(clubStatistics -> clubStatistics.getMatch().getId().equals(foundMatch.getId()))
                        .toList());
                if(clubStatistic.isEmpty()){
                    clubStatistic.add(this.clubStatisticsDAO.saveWithMatchIdAndSeasonId(new ClubStatistics(
                            UUID.randomUUID(),
                            0,
                            0,
                            0,
                            0,
                            0
                    ), clubHomeId, foundMatch.getSeason().getId(), foundMatch.getId()));
                }
                clubHome.getClubScore().getClub().setClubStatistics(clubStatistic);

                this.clubScoreDAO.saveGoalInMatch(foundMatch.getId(), scorer, clubHome.getClubScore());
            }

            if(scorerClubId.equals(clubAwayId)){
                Player foundPlayer = foundMatch.getClubPlayingAway().getClubScore().getClub().getPlayers().stream()
                        .filter(player -> player.getId().equals(scorerId)).toList().getFirst();
                if(foundPlayer == null){
                    scorer.setOwnGoal(true);
                }
                List<ClubStatistics> clubStatistic = new ArrayList<>(clubAway.getClubScore().getClub().getClubStatistics()
                        .stream().filter(clubStatistics -> clubStatistics.getMatch().getId().equals(foundMatch.getId()))
                        .toList());
                if(clubStatistic.isEmpty()){
                    clubStatistic.add(this.clubStatisticsDAO.saveWithMatchIdAndSeasonId(new ClubStatistics(
                            UUID.randomUUID(),
                            0,
                            0,
                            0,
                            0,
                            0
                    ), clubAwayId, foundMatch.getSeason().getId(), foundMatch.getId()));
                }
                clubAway.getClubScore().getClub().setClubStatistics(clubStatistic);

                this.clubScoreDAO.saveGoalInMatch(foundMatch.getId(), scorer, clubAway.getClubScore());
            }
        });

        return this.save(foundMatch);
    }

    @Override
    public Match getById(String id) {
        UUID validId = IdVerification.validUUID(id);

        Match foundMatch = this.matchDAO.findById(validId);
        if(foundMatch == null){
            throw new ClientException("Match with id : " + id + " not found");
        }

        return foundMatch;
    }

    @Override
    public Match save(Match match) {
        return this.matchDAO.save(match);
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
