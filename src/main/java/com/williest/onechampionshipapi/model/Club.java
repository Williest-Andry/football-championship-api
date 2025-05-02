package com.williest.onechampionshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Club {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private String name;
    private LocalDate creationYear;
    private String acronym;
    private String stadiumName;
    private League league;
    private List<Player> players;
    private Coach coach;
    private ClubStatistic clubStatistic;
    private Ranking ranking;

    public int getYearCreation(){
        return creationYear.getYear();
    }
}
