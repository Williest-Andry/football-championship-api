package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Player {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private int number;
    private String nationality;
    private String birth_year;
    private PlayerPosition playerPosition;
    private Club club;
    private List<PlayerStatistics> playerStatistics;

    public int getAge(){
        return LocalDate.now().getYear() - Integer.parseInt(birth_year);
    }
}