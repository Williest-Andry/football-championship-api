package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Club {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private String name;
    private String acronym;
    private String yearCreation;
    private String stadium;
    private League league;
    private Coach coach;
    private List<ClubStatistics> clubStatistics;
    private List<Player> players;

    public void setPlayers(List<Player> players) {
        players.forEach(player -> player.setClub(this));
        this.players = players;
    }

    public void addPlayers(List<Player> players) {
        players.forEach(player -> {
            this.players.add(player);
            player.setClub(this);
        });
    }

    public ClubStatistics getGeneralClubStatistics() {
        Integer totalScoredGoals = this.getClubStatistics().stream().map(EntityStatistics::getScoredGoals).reduce(Integer::sum).orElse(0);

        List<Integer> rankingPoints = new ArrayList<>();
        this.getClubStatistics().forEach(clubStatistics -> {
            List<Integer> clubVictories = new ArrayList<>();
            List<Integer> clubDraws = new ArrayList<>();

            if(clubStatistics.getScoredGoals() > clubStatistics.getConcededGoals()){
                clubVictories.add(1);
            }
            if(clubStatistics.getScoredGoals() == clubStatistics.getConcededGoals()){
                clubDraws.add(1);
            }

            Integer totalVictories = clubVictories.stream().map(victory -> victory * 3).reduce(0, Integer::sum);
            Integer totalDraws = clubDraws.stream().reduce(0, Integer::sum);

            rankingPoints.add(totalVictories + totalDraws);
        });
        Integer totalRankingPoints = rankingPoints.stream().reduce(0, Integer::sum);

        Integer totalConcededGoals = this.getClubStatistics().stream().map(ClubStatistics::getConcededGoals).reduce(Integer::sum).orElse(0);

        List<Integer> cleanSheets = new ArrayList<>();
        this.getClubStatistics().forEach(statistics -> {
            if (statistics.getConcededGoals() == 0) {
                cleanSheets.add(1);
            }
        });
        Integer totalCleanSheets = cleanSheets.stream().reduce(0, Integer::sum);

        return new ClubStatistics(
                null,
                totalScoredGoals,
                totalRankingPoints,
                totalConcededGoals,
                totalScoredGoals - totalConcededGoals,
                totalCleanSheets
        );
    }
}
