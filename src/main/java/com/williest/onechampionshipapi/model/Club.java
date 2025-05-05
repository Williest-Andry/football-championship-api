package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

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
    private Ranking ranking;

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
}
