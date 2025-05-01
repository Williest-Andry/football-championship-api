package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Club {
    private String id;
    private String name;
    private LocalDate creationYear;
    private String acronym;
    private String stadiumName;
    private League league;
    private List<Player> players;
    private Coach coach;
    private ClubStatistic clubStatistic;
}
